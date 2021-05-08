package com.beanframework.modulegen;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.beanframework.modulegen.create.CreateModule;
import com.beanframework.modulegen.create.CreateModuleBackoffice;
import com.beanframework.modulegen.create.CreateModuleCore;

@Component
public class ModulegenCommandLiner implements CommandLineRunner {

	@Value("${modulegen.modulegroup}")
	private String modulegroup;

	@Value("${modulegen.moduleartifact}")
	private String moduleartifact;

	@Value("${modulegen.moduleversion}")
	private String moduleversion;

	@Value("${modulegen.modulelocation}")
	private String modulelocation;

	@Autowired
	private CreateModule createModule;

	@Autowired
	private CreateModuleCore createModuleCore;

	@Autowired
	private CreateModuleBackoffice createModuleBackoffice;

	@Override
	public void run(String... args) throws Exception {
		VelocityContext context = new VelocityContext();
		context.put("modulegroup", modulegroup);
		context.put("MODULEARTIFACT", moduleartifact.toUpperCase());
		context.put("Moduleartifact", StringUtils.capitalize(moduleartifact.toLowerCase()));
		context.put("moduleartifact", moduleartifact.toLowerCase());
		context.put("moduleversion", moduleversion);
		context.put("startbrace", "${");
		context.put("endbrace", "}");

		createModule.create(context, moduleartifact, modulegroup, modulelocation);
		createModuleCore.create(context, moduleartifact, modulegroup, modulelocation);
		createModuleBackoffice.create(context, moduleartifact, modulegroup, modulelocation);
	}
}
