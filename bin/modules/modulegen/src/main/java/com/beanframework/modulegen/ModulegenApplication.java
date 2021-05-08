package com.beanframework.modulegen;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ModulegenApplication {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(ModulegenApplication.class).web(WebApplicationType.NONE).run(args);
	}

	@Bean
	public VelocityEngine velocityEngine() throws Exception {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.init();
		return velocityEngine;
	}

}
