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
public class CreateModule {

	@Autowired
	private VelocityEngine velocityEngine;

	public void create(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		createClassPathModule(context, moduleartifact, modulegroup, location);
		createPom(context, moduleartifact, modulegroup, location);
		createConstants(context, moduleartifact, modulegroup, location);
		createDomain(context, moduleartifact, modulegroup, location);
	}
	
	private void createClassPathModule(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact;

		Template template = velocityEngine.getTemplate("templates/module/classpath.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + ".classpath");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// sample\pom.xml
	private void createPom(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact;

		Template template = velocityEngine.getTemplate("templates/module/pom.xml.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + "pom.xml");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// sample\src\main\java\com\beanframework\sample\SampleConstants.java
	private void createConstants(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact;

		Template template = velocityEngine.getTemplate("templates/module/ModuleConstants.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Constants.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// sample\src\main\java\com\beanframework\sample\domain\Sample.java
	private void createDomain(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + File.separator + "domain";

		Template template = velocityEngine.getTemplate("templates/module/Module.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + ".java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}
}
