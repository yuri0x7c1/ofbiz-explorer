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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.yuri0x7c1.ofbiz.explorer.service.util.ServiceParameter;
import com.github.yuri0x7c1.ofbiz.explorer.service.util.ServiceUtil;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceGenerator {
	@Getter
	@Setter
	private OfbizInstance ofbizInstance;

	@Getter
	@Setter
	private Service service;

	public String generate() throws Exception {
		if (service == null ) throw new Exception("Service must not be null");

		String packageName = ServiceUtil.locationToPackageName(service.getLocation());
		log.info("package name {}", packageName);

		String serviceClassName = StringUtils.capitalize(service.getName()) + "Service";
		JavaClassSource serviceSource = Roaster.create(JavaClassSource.class)
			.setName(serviceClassName)
			.setPackage(packageName);

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

		List<ServiceParameter> inParams = ServiceUtil.getServiceInParameters(service, ofbizInstance);
		List<ServiceParameter> outParams = ServiceUtil.getServiceOutParameters(service, ofbizInstance);

		/* process service IN params */
		if (!inParams.isEmpty()) {

			// crate In nested type
			JavaClassSource inTypeSource = Roaster.create(JavaClassSource.class).setName("In");

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
					.setType(ServiceUtil.getParameterType(param))
					.setPrivate();

				fieldSource.addAnnotation(Getter.class);
				fieldSource.addAnnotation(Setter.class);

				// append param to "toMap()" body
				toMapMethodBody.append(String.format("map.put(\"%s\", %s);", paramKey, paramName));

				// append param to "fromMap()" body
				fromMapMethodBody.append(String.format("result.set%s(%s map.get(\"%s\"));",
						StringUtils.capitalize(paramName),
						"(" + ServiceUtil.getParameterType(param).getSimpleName() + ")",
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

			// add "fromMap() method return value
			fromMapMethodBody.append("return result;");

			// add "fromMap()" method to nested "In" type
			MethodSource<JavaClassSource> fromMapSource = inTypeSource.addMethod()
				.setName("fromMap")
				.setReturnType(inTypeSource)
				.setPublic()
				.setStatic(true)
				.setBody(fromMapMethodBody.toString());
			fromMapSource.addParameter(Map.class, "map");

			// add In nested type to service class
			serviceSource.addNestedType(inTypeSource)
				.setPublic()
				.setStatic(true);

			// add "in" field
			FieldSource<JavaClassSource> inFieldSource = serviceSource.addField()
				.setName("in")
				.setType(inTypeSource)
				.setPrivate();

			inFieldSource.addAnnotation(Getter.class);
			inFieldSource.addAnnotation(Setter.class);
		}

		/* process service OUT params */
		if (!outParams.isEmpty()) {

			// crate In nested type
			JavaClassSource outTypeSource = Roaster.create(JavaClassSource.class).setName("Out");

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
					.setType(ServiceUtil.getParameterType(param))
					.setPrivate();

				fieldSource.addAnnotation(Getter.class);
				fieldSource.addAnnotation(Setter.class);

				// append param to "toMap()" body
				toMapMethodBody.append(String.format("map.put(\"%s\", %s);", param.getName(), param.getName()));

				// append param to "fromMap()" body
				fromMapMethodBody.append(String.format("result.set%s(%smap.get(\"%s\"));",
						StringUtils.capitalize(param.getName()),
						"(" + ServiceUtil.getParameterType(param).getSimpleName() + ")",
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

			// add "fromMap() method return value
			fromMapMethodBody.append("return result;");

			// add "fromMap()" method to nested "Out" type
			MethodSource<JavaClassSource> fromMapSource = outTypeSource.addMethod()
				.setName("fromMap")
				.setReturnType(outTypeSource)
				.setPublic()
				.setStatic(true)
				.setBody(fromMapMethodBody.toString());
			fromMapSource.addParameter(Map.class, "map");

			// add Out nested type to service class
			serviceSource.addNestedType(outTypeSource)
				.setPublic()
				.setStatic(true);

			// add "out" field
			FieldSource<JavaClassSource> inFieldSource = serviceSource.addField()
				.setName("out")
				.setType(outTypeSource)
				.setPrivate();

			inFieldSource.addAnnotation(Getter.class);
			inFieldSource.addAnnotation(Setter.class);
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
				+ "return Out.fromMap(result);"
			);

		File src = new File(FilenameUtils.concat("/tmp/services", GeneratorUtil.packageNameToPath(packageName)), serviceClassName + ".java");

		FileUtils.writeStringToFile(src,  serviceSource.toString());

		return serviceSource.toString();
	}
}
