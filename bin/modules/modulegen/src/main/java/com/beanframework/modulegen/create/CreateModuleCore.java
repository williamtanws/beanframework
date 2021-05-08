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
public class CreateModuleCore {

	@Autowired
	private VelocityEngine velocityEngine;

	public void create(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		createClassPathModuleBackoffice(context, moduleartifact, modulegroup, location);
		createPom(context, moduleartifact, modulegroup, location);
		createImportListenerConstants(context, moduleartifact, modulegroup, location);
		createModuleConfig(context, moduleartifact, modulegroup, location);
		createEntityCsvConverterConfig(context, moduleartifact, modulegroup, location);
		createDtoConfig(context, moduleartifact, modulegroup, location);
		createEntityConfig(context, moduleartifact, modulegroup, location);
		createInterceptorConfig(context, moduleartifact, modulegroup, location);
		createDtoConverter(context, moduleartifact, modulegroup, location);
		createEntityConverter(context, moduleartifact, modulegroup, location);
		createCsvEntityConverter(context, moduleartifact, modulegroup, location);
		createPopulator(context, moduleartifact, modulegroup, location);
		createCsv(context, moduleartifact, modulegroup, location);
		createDto(context, moduleartifact, modulegroup, location);
		createFacade(context, moduleartifact, modulegroup, location);
		createFacadeImpl(context, moduleartifact, modulegroup, location);
		createImportListener(context, moduleartifact, modulegroup, location);
		createInitialDefaultsInterceptor(context, moduleartifact, modulegroup, location);
		createLoadInterceptor(context, moduleartifact, modulegroup, location);
		createPrepareInterceptor(context, moduleartifact, modulegroup, location);
		createRemoveInterceptor(context, moduleartifact, modulegroup, location);
		createValidateInterceptor(context, moduleartifact, modulegroup, location);
		createAfterRemoveListener(context, moduleartifact, modulegroup, location);
		createAfterSaveListener(context, moduleartifact, modulegroup, location);
		createBeforeRemoveListener(context, moduleartifact, modulegroup, location);
		createBeforeSaveListener(context, moduleartifact, modulegroup, location);
		createListener(context, moduleartifact, modulegroup, location);
		createSpecification(context, moduleartifact, modulegroup, location);
		create_en(context, moduleartifact, modulegroup, location);
		create_cn(context, moduleartifact, modulegroup, location);
		create_menu(context, moduleartifact, modulegroup, location);
		create_userpermission(context, moduleartifact, modulegroup, location);
		create_userauthority(context, moduleartifact, modulegroup, location);
		createModule(context, moduleartifact, modulegroup, location);
	}

	private void createClassPathModuleBackoffice(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core";

		Template template = velocityEngine.getTemplate("templates/modulecore/classpath.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + ".classpath");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\pom.xml
	private void createPom(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core";

		Template template = velocityEngine.getTemplate("templates/modulecore/pom.xml.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + "pom.xml");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\SampleImportListenerConstants.java
	private void createImportListenerConstants(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleImportListenerConstants.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "ImportListenerConstants.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\config\SampleConfig.java
	private void createModuleConfig(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "config";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleConfig.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Config.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\config\EntityCsvConverterConfig.java
	private void createEntityCsvConverterConfig(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "config";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleEntityCsvConverterConfig.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "EntityCsvConverterConfig.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\config\dto\SampleDtoConfig.java
	private void createDtoConfig(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "config"
				+ File.separator + "dto";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleDtoConfig.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "DtoConfig.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\config\dto\SampleDtoConfig.java
	private void createEntityConfig(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "config"
				+ File.separator + "entity";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleEntityConfig.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "EntityConfig.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\config\interceptor\SampleInterceptorConfig.java
	private void createInterceptorConfig(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "config"
				+ File.separator + "interceptor";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleInterceptorConfig.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "InterceptorConfig.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\converter\dto\SampleDtoConverter.java
	private void createDtoConverter(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "converter"
				+ File.separator + "dto";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleDtoConverter.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "DtoConverter.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\converter\entity\SampleEntityConverter.java
	private void createEntityConverter(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "converter"
				+ File.separator + "entity";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleEntityConverter.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "EntityConverter.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\converter\entity\csv\SampleCsvEntityConverter.java
	private void createCsvEntityConverter(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "converter"
				+ File.separator + "entity" + File.separator + "csv";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleCsvEntityConverter.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "CsvEntityConverter.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\converter\populator\SamplePopulator.java
	private void createPopulator(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "converter"
				+ File.separator + "populator";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModulePopulator.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Populator.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\csv\SampleCsv.java
	private void createCsv(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "csv";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleCsv.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Csv.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\data\SampleDto.java
	private void createDto(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "data";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleDto.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Dto.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\facade\SampleFacade.java
	private void createFacade(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "facade";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleFacade.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Facade.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\facade\SampleFacadeImpl.java
	private void createFacadeImpl(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "facade";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleFacadeImpl.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "FacadeImpl.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\importlistener\SampleImportListener.java
	private void createImportListener(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator
				+ "importlistener";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleImportListener.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "ImportListener.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\interceptor\SampleInitialDefaultsInterceptor.java
	private void createInitialDefaultsInterceptor(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "interceptor";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleInitialDefaultsInterceptor.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "InitialDefaultsInterceptor.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\interceptor\SampleLoadInterceptor.java
	private void createLoadInterceptor(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "interceptor";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleLoadInterceptor.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "LoadInterceptor.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\interceptor\SamplePrepareInterceptor.java
	private void createPrepareInterceptor(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "interceptor";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModulePrepareInterceptor.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "PrepareInterceptor.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\interceptor\SampleRemoveInterceptor.java
	private void createRemoveInterceptor(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "interceptor";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleRemoveInterceptor.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "RemoveInterceptor.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\interceptor\SampleValidateInterceptor.java
	private void createValidateInterceptor(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "interceptor";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleValidateInterceptor.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "ValidateInterceptor.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\listener\SampleAfterRemoveListener.java
	private void createAfterRemoveListener(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "listener";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleAfterRemoveListener.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "AfterRemoveListener.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\listener\SampleAfterSaveListener.java
	private void createAfterSaveListener(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "listener";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleAfterSaveListener.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "AfterSaveListener.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\listener\SampleBeforeRemoveListener.java
	private void createBeforeRemoveListener(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "listener";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleBeforeRemoveListener.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "BeforeRemoveListener.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\listener\SampleBeforeSaveListener.java
	private void createBeforeSaveListener(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "listener";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleBeforeSaveListener.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "BeforeSaveListener.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\listener\SampleListener.java
	private void createListener(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "listener";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleListener.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Listener.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\java\com\beanframework\samplecore\specification\TrainingSpecification.java
	private void createSpecification(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/java/" + modulegroup.replace(".", "/") + File.separator + moduleartifact + "core" + File.separator + "specification";

		Template template = velocityEngine.getTemplate("templates/modulecore/ModuleSpecification.java.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + StringUtils.capitalize(moduleartifact.toLowerCase()) + "Specification.java");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\resource\i18n\sample_en.properties
	private void create_en(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/resources/i18n";

		Template template = velocityEngine.getTemplate("templates/modulecore/module_en.properties.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + moduleartifact.toLowerCase() + "_en.properties");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\resource\i18n\sample_cn.properties
	private void create_cn(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/resources/i18n";

		Template template = velocityEngine.getTemplate("templates/modulecore/module_cn.properties.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + moduleartifact.toLowerCase() + "_cn.properties");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\resource\import\dev\ updatedate\sample\sample_menu.csv
	private void create_menu(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/resources/import/dev/updatedata/100-" + moduleartifact;

		Template template = velocityEngine.getTemplate("templates/modulecore/100-module_menu.csv.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + "100-" + moduleartifact.toLowerCase() + "_menu.csv");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\resource\import\dev\
	// updatedate\sample\sample_userpermission.csv
	private void create_userpermission(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/resources/import/dev/updatedata/100-" + moduleartifact;

		Template template = velocityEngine.getTemplate("templates/modulecore/101-module_userpermission.csv.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + "101-" + moduleartifact.toLowerCase() + "_userpermission.csv");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\resource\import\dev\
	// updatedate\sample\sample_userpermission.csv
	private void create_userauthority(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/resources/import/dev/updatedata/100-" + moduleartifact;

		Template template = velocityEngine.getTemplate("templates/modulecore/102-module_userauthority.csv.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + "102-" + moduleartifact.toLowerCase() + "_userauthority.csv");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}

	// samplecore\src\main\resource\import\dev\ updatedate\sample\sample.csv
	private void createModule(VelocityContext context, String moduleartifact, String modulegroup, String location) throws IOException {
		String path = location + File.separator + moduleartifact + "core" + File.separator + "src/main/resources/import/dev/updatedata/100-" + moduleartifact;

		Template template = velocityEngine.getTemplate("templates/modulecore/103-module.csv.vm");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		File moduleEntity = new File(path + File.separator + "103-" + moduleartifact.toLowerCase() + ".csv");
		FileUtils.write(moduleEntity, writer.toString(), StandardCharsets.UTF_8.name());
		System.out.println("Written: " + moduleEntity.getPath());
	}
}
