package com.beanframework.modulegen.create;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateModuleBackoffice {

	@Autowired
	private VelocityEngine velocityEngine;

	public void create(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		createClassPathModuleCore(context, moduleartifact, modulegroup, location);
		createPom(context, moduleartifact, modulegroup, location);
		createConstants(context, moduleartifact, modulegroup, location);
		createResource(context, moduleartifact, modulegroup, location);
		createDataTableResponseData(context, moduleartifact, modulegroup, location);
		createController(context, moduleartifact, modulegroup, location);
		createApplication(context, moduleartifact, modulegroup, location);
	}
	
	private void createClassPathModuleCore(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "backoffice";

		Template template = velocityEngine.getTemplate("templates/modulebackoffice/classpath.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + ".classpath");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplebackoffice\pom.xml
	private void createPom(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "backoffice";

		Template template = velocityEngine.getTemplate("templates/modulebackoffice/pom.xml.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + "pom.xml");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplebackoffice\src\main\java\com\beanframework\samplebackoffice\SampleWebConstants.java
	private void createConstants(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "backoffice" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "backoffice";

		Template template = velocityEngine.getTemplate("templates/modulebackoffice/ModuleWebConstants.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "WebConstants.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplebackoffice\src\main\java\com\beanframework\samplebackoffice\api\SampleResource.java
	private void createResource(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "backoffice" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "backoffice" + File.separator
				+ "api";

		Template template = velocityEngine.getTemplate("templates/modulebackoffice/ModuleResource.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Resource.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplebackoffice\src\main\java\com\beanframework\samplebackoffice\api\data\SampleDataTableResponseData.java
	private void createDataTableResponseData(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "backoffice" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "backoffice" + File.separator
				+ "api" + File.separator + "data";

		Template template = velocityEngine.getTemplate("templates/modulebackoffice/ModuleDataTableResponseData.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "DataTableResponseData.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplebackoffice\src\main\java\com\beanframework\samplebackoffice\web\SampleController.java
	private void createController(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "backoffice" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "backoffice" + File.separator
				+ "web";

		Template template = velocityEngine.getTemplate("templates/modulebackoffice/ModuleController.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Controller.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplebackoffice\src\main\resource\application-sample.properties
	private void createApplication(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "backoffice" + File.separator + "src/main/resources/";

		Template template = velocityEngine.getTemplate("templates/modulebackoffice/application-module.properties.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + "application-" + moduleartifact.toLowerCase() + ".properties");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}
}
