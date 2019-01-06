
package com.beanframework.console.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.console.PlatformUpdateWebConstants;
import com.beanframework.console.importer.AdminImporter;
import com.beanframework.console.importer.ConfigurationImporter;
import com.beanframework.console.importer.CronjobImporter;
import com.beanframework.console.importer.CustomerImporter;
import com.beanframework.console.importer.DynamicFieldImporter;
import com.beanframework.console.importer.EmployeeImporter;
import com.beanframework.console.importer.LanguageImporter;
import com.beanframework.console.importer.MenuImporter;
import com.beanframework.console.importer.UserAuthorityImporter;
import com.beanframework.console.importer.UserGroupImporter;
import com.beanframework.console.importer.UserPermissionImporter;
import com.beanframework.console.importer.UserRightImporter;
import com.beanframework.console.registry.ImporterRegistry;

@Configuration
public class PlatformUpdateConfig implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private ImporterRegistry importerRegistry;

	@Value("#{'${module.console.import.keys}'.split(',')}")
	private List<String> importKeyList;

	@Bean
	public AdminImporter adminImporter() {
		return new AdminImporter();
	}

	@Bean
	public ConfigurationImporter configurationImporter() {
		return new ConfigurationImporter();
	}

	@Bean
	public DynamicFieldImporter dynamicFieldImporter() {
		return new DynamicFieldImporter();
	}

	@Bean
	public EmployeeImporter employeeImporter() {
		return new EmployeeImporter();
	}

	@Bean
	public LanguageImporter languageImporter() {
		return new LanguageImporter();
	}

	@Bean
	public MenuImporter menuImporter() {
		return new MenuImporter();
	}

	@Bean
	public UserPermissionImporter userPermissionImporter() {
		return new UserPermissionImporter();
	}

	@Bean
	public UserGroupImporter userGroupImporter() {
		return new UserGroupImporter();
	}

	@Bean
	public UserRightImporter userRightImporter() {
		return new UserRightImporter();
	}

	@Bean
	public UserAuthorityImporter userAuthorityImporter() {
		return new UserAuthorityImporter();
	}

	@Bean
	public CustomerImporter customerImporter() {
		return new CustomerImporter();
	}

	@Bean
	public CronjobImporter cronjobImporter() {
		return new CronjobImporter();
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.AdminImporter.KEY))
			importerRegistry.addImporter(adminImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.ConfigurationImporter.KEY))
			importerRegistry.addImporter(configurationImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.CronjobImporter.KEY))
			importerRegistry.addImporter(cronjobImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.CustomerImporter.KEY))
			importerRegistry.addImporter(customerImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.DynamicFieldImporter.KEY))
			importerRegistry.addImporter(dynamicFieldImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.EmployeeImporter.KEY))
			importerRegistry.addImporter(employeeImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.LanguageImporter.KEY))
			importerRegistry.addImporter(languageImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.MenuImporter.KEY))
			importerRegistry.addImporter(menuImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.UserAuthorityImporter.KEY))
			importerRegistry.addImporter(userAuthorityImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.UserGroupImporter.KEY))
			importerRegistry.addImporter(userGroupImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.UserPermissionImporter.KEY))
			importerRegistry.addImporter(userPermissionImporter());

		if (importKeyList.contains(PlatformUpdateWebConstants.Importer.UserRightImporter.KEY))
			importerRegistry.addImporter(userRightImporter());
	}
}