package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.io.File;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.yuri0x7c1.ofbiz.explorer.service.util.ServiceParameter;
import com.github.yuri0x7c1.ofbiz.explorer.service.util.ServiceUtil;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServiceFormGenerator {

	public static final String SUBPACKAGE_NAME = "ui.form";
	public static final String TYPE_NAME_PART = "ServiceForm";
	public static final String FIELD_VARIABLE_PART = "Field";

	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private ServiceUtil serviceUtil;

	@Autowired
	private ServiceGenerator serviceGenerator;

	@Autowired
	private Environment env;

	public String getPackageName(Service service) {
		return serviceUtil.locationToPackageName(service.getLocation()) + "." + SUBPACKAGE_NAME;
	}

	public String getTypeSimpleName(Service service) {
		return StringUtils.capitalize(service.getName()) + TYPE_NAME_PART;
	}

	public String getTypeName(Service service) {
		return getPackageName(service) + "." + getTypeSimpleName(service);
	}

	public String getVariableName(Service service) {
		return StringUtils.uncapitalize(getTypeSimpleName(service));
	}

	private String getFieldVariableName(ServiceParameter param) {
		return param.getName() + FIELD_VARIABLE_PART;
	}

	public String generate(Service service) throws Exception {
		if (service == null ) throw new Exception("Service must not be null");

		String packageName = getPackageName(service);
		log.info("Service form package name {}", packageName);

		String serviceFormClassName = getTypeSimpleName(service);
		JavaClassSource serviceFormSource = Roaster.create(JavaClassSource.class)
			.setName(serviceFormClassName)
			.setSuperType(VerticalLayout.class)
			.setPackage(packageName);

		serviceFormSource.addAnnotation(Slf4j.class);
		serviceFormSource.addAnnotation(SpringComponent.class);
		serviceFormSource.addAnnotation(UIScope.class);

		// add bean field
		serviceFormSource.addField()
			.setName("bean")
			.setType(serviceGenerator.getFullTypeName(service) + ".In")
			.setPrivate()
			.addAnnotation(Getter.class);

		// add setBean method
		serviceFormSource.addMethod()
			.setName("setBean")
			.setPublic()
			.setBody("this.bean = bean; binder.setBean(bean);")
			.addParameter(serviceGenerator.getTypeName(service) + ".In", "bean");

		// add binder field
		serviceFormSource.addField()
			.setName("binder")
			.setType(Binder.class.getName() + "<" + serviceGenerator.getTypeName(service) + ".In>")
			.setLiteralInitializer("new " + Binder.class.getSimpleName() + "<>();")
			.setPrivate();

		// get service params list
		Collection<ServiceParameter> inParams = serviceUtil.getServiceInParameters(service);

		StringBuilder bindFieldsSource = new StringBuilder();
		StringBuilder addComponentsSource = new StringBuilder();

		// process fields
		for (ServiceParameter param : inParams) {
			if (!param.isSetInternally()) {
				// form field definition
				FieldSource<JavaClassSource> formFieldSource = 	serviceFormSource.addField()
						.setName(getFieldVariableName(param))
						.setPrivate();
				formFieldSource.addAnnotation(Getter.class);

				// determine form field type
				if (param.isText() || param.isNumeric()) {
					formFieldSource.setType(TextField.class);
					formFieldSource.setLiteralInitializer("new " + TextField.class.getSimpleName() + "(\"" + param.getName() + "\")");
				}
				else if (param.isBoolean()) {
					formFieldSource.setType(CheckBox.class);
					formFieldSource.setLiteralInitializer("new " + CheckBox.class.getSimpleName() + "(\"" + param.getName() + "\")");
				}
				else if (param.isTemporal()) {
					formFieldSource.setType(DateField.class);
					formFieldSource.setLiteralInitializer("new " + DateField.class.getSimpleName() + "(\"" + param.getName() + "\")");
				}

				// bind fields source
				bindFieldsSource.append(String.format("binder.forField(%s).bind(%s.In::%s, %s.In::%s);",
						getFieldVariableName(param),
						serviceGenerator.getTypeName(service), param.getterName(),
						serviceGenerator.getTypeName(service), param.setterName()));

				// add components source
				addComponentsSource.append("addComponent(" + getFieldVariableName(param) + ");");
			}
		}

		// add run button field
		serviceFormSource.addField()
			.setName("runButton")
			.setType(Button.class)
			.setPrivate()
			.setLiteralInitializer("new Button(\"Run\");")
			.addAnnotation(Getter.class);

		// init method source
		MethodSource<JavaClassSource> initMethodSource = serviceFormSource.addMethod()
				.setName("init")
				.setPrivate()
				.setBody(bindFieldsSource.toString()
						+ addComponentsSource.toString()
						+ "addComponent(runButton);");
		initMethodSource.addAnnotation(PostConstruct.class);

		// imports hack
		serviceFormSource.removeImport(serviceGenerator.getTypeName(service) + ".In");
		serviceFormSource.addImport(serviceGenerator.getFullTypeName(service));

		String destinationPath = env.getProperty("generator.destination_path");

		File src = new File(FilenameUtils.concat(destinationPath, GeneratorUtil.packageNameToPath(packageName)), serviceFormClassName + ".java");

		FileUtils.writeStringToFile(src,  serviceFormSource.toString());

		return serviceFormSource.toString();
	}
}
