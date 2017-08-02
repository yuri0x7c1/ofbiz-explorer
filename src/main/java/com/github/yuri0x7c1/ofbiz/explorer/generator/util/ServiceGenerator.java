package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.yuri0x7c1.ofbiz.explorer.service.util.ServiceParameter;
import com.github.yuri0x7c1.ofbiz.explorer.service.util.ServiceUtil;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServiceGenerator {
	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private Environment env;

	@Autowired
	private ServiceUtil serviceUtil;

	public String getPackageName(Service service) {
		return serviceUtil.locationToPackageName(service.getLocation());
	}

	public String getTypeName(Service service) {
		return StringUtils.capitalize(service.getName()) + "Service";
	}

	public String getFullTypeName(Service service) {
		return getPackageName(service) + "." + getTypeName(service);
	}

	public String getVariableName(Service service) {
		return StringUtils.uncapitalize(getTypeName(service));
	}

	public String generate(Service service) throws Exception {
		if (service == null ) throw new Exception("Service must not be null");

		String packageName = serviceUtil.locationToPackageName(service.getLocation());
		log.info("package name {}", packageName);

		String serviceClassName = getTypeName(service);
		JavaClassSource serviceSource = Roaster.create(JavaClassSource.class)
			.setName(serviceClassName)
			.setPackage(packageName);

		// some imports
		serviceSource.addImport(Getter.class);
		serviceSource.addImport(Setter.class);
		serviceSource.addImport(NoArgsConstructor.class);

		serviceSource.addAnnotation(Component.class);
		serviceSource.addAnnotation(Slf4j.class);

		// add static service name field
		serviceSource.addField()
			.setName("NAME")
			.setType(String.class)
			.setStringInitializer(service.getName())
			.setPublic()
			.setStatic(true)
			.setFinal(true);

		// add dispatcher autowired field
		serviceSource.addField()
			.setName("dispatcher")
			.setType("org.apache.ofbiz.service.LocalDispatcher")
			.setPrivate()
			.addAnnotation(Autowired.class);

		List<ServiceParameter> inParams = serviceUtil.getServiceInParameters(service);
		List<ServiceParameter> outParams = serviceUtil.getServiceOutParameters(service);

		/* process service IN params */

		// crate In nested type
		JavaClassSource inTypeSource = Roaster.create(JavaClassSource.class).setName("In");
		inTypeSource.addAnnotation(NoArgsConstructor.class);

		if (!inParams.isEmpty()) {
			// some outer class imports
			serviceSource.addImport(Map.class);
			serviceSource.addImport(HashMap.class);
			serviceSource.addImport(List.class);

			// toMap method body
			StringBuilder toMapMethodBody = new StringBuilder("Map map = new HashMap();");
			StringBuilder fromMapMethodBody = new StringBuilder("In result = new In();");

			// create In type fields
			for (ServiceParameter param : inParams) {
				String paramName = param.getName();
				String paramKey = param.getName();
				if ("login.username".equals(param.getName())) {
					paramName = "username";
				}
				else if ("login.password".equals(param.getName())) {
					paramName = "password";
				}
				FieldSource<JavaClassSource> fieldSource = inTypeSource.addField()
					.setName(paramName)
					.setType(param.getJavaType())
					.setPrivate();

				fieldSource.addAnnotation(Getter.class);
				fieldSource.addAnnotation(Setter.class);

				// append param to "toMap()" body
				toMapMethodBody.append(String.format("map.put(\"%s\", %s);", paramKey, paramName));

				// append param to "fromMap()" body
				fromMapMethodBody.append(String.format("%s = %s map.get(\"%s\");",
						paramName,
						"(" + param.getJavaType().getSimpleName() + ")",
						paramKey));
			}

			// add "toMap() method return value
			toMapMethodBody.append("return map;");

			// add "toMap()" method to nested "In" type
			inTypeSource.addMethod()
				.setName("toMap")
				.setReturnType(Map.class)
				.setPublic()
				.setBody(toMapMethodBody.toString());

			// add "fromMap()" method to nested "In" type
			MethodSource<JavaClassSource> fromMapSource = inTypeSource.addMethod()
				.setName("fromMap")
				.setPublic()
				.setBody(fromMapMethodBody.toString());
			fromMapSource.addParameter(Map.class, "map");

			// add constructor
			inTypeSource.addMethod()
				.setConstructor(true)
				.setBody("fromMap(map);")
				.setPublic()
				.addParameter(Map.class, "map");

			// add In nested type to service class
			serviceSource.addNestedType(inTypeSource)
				.setPublic()
				.setStatic(true);
		}

		/* process service OUT params */

		// crate Out nested type
		JavaClassSource outTypeSource = Roaster.create(JavaClassSource.class).setName("Out");
		outTypeSource.addAnnotation(NoArgsConstructor.class);

		if (!outParams.isEmpty()) {

			// some outer class imports
			serviceSource.addImport(Map.class);
			serviceSource.addImport(HashMap.class);
			serviceSource.addImport(List.class);

			// toMap method body
			StringBuilder toMapMethodBody = new StringBuilder("Map map = new HashMap();");
			StringBuilder fromMapMethodBody = new StringBuilder("Out result = new Out();");

			// create In type fields
			for (ServiceParameter param : outParams) {
				FieldSource<JavaClassSource> fieldSource = outTypeSource.addField()
					.setName(param.getName())
					.setType(param.getJavaType())
					.setPrivate();

				fieldSource.addAnnotation(Getter.class);
				fieldSource.addAnnotation(Setter.class);

				// append param to "toMap()" body
				toMapMethodBody.append(String.format("map.put(\"%s\", %s);", param.getName(), param.getName()));

				// append param to "fromMap()" body
				fromMapMethodBody.append(String.format("%s = %s map.get(\"%s\");",
						param.getName(),
						"(" + param.getJavaType().getSimpleName() + ")",
						param.getName()));
			}

			// add "toMap() method return value
			toMapMethodBody.append("return map;");

			// add "toMap()" method to nested "Out" type
			outTypeSource.addMethod()
				.setName("toMap")
				.setReturnType(Map.class)
				.setPublic()
				.setBody(toMapMethodBody.toString());

			// add "fromMap()" method to nested "Out" type
			MethodSource<JavaClassSource> fromMapSource = outTypeSource.addMethod()
				.setName("fromMap")
				.setPublic()
				.setBody(fromMapMethodBody.toString());
			fromMapSource.addParameter(Map.class, "map");

			// add constructor
			outTypeSource.addMethod()
				.setConstructor(true)
				.setBody("fromMap(map);")
				.setPublic()
				.addParameter(Map.class, "map");

			// add Out nested type to service class
			serviceSource.addNestedType(outTypeSource)
				.setPublic()
				.setStatic(true);
		}

		/* add "runSync()" method */
		serviceSource.addMethod()
			.setName("runSync")
			.setPublic()
			.setBody("Map result = null;"
				+ "try {"
				+ "	result = dispatcher.runSync(NAME, in.toMap());"
				+ "}"
				+ "catch (Exception e) {"
				+ "	log.error(\"Error\", e);"
				+ "}"
				+ "return new Out(result);"
			)
			.setReturnType(outTypeSource)
			.addParameter(inTypeSource, "in");

		String destinationPath = env.getProperty("generator.destination_path");

		File src = new File(FilenameUtils.concat(destinationPath, GeneratorUtil.packageNameToPath(packageName)), serviceClassName + ".java");

		FileUtils.writeStringToFile(src,  serviceSource.toString());

		return serviceSource.toString();
	}
}
