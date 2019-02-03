
package com.beanframework.console.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.listener.AdminImportListener;
import com.beanframework.console.listener.ConfigurationImportListener;
import com.beanframework.console.listener.CronjobImportListener;
import com.beanframework.console.listener.CustomerImportListener;
import com.beanframework.console.listener.DynamicFieldImportListener;
import com.beanframework.console.listener.DynamicFieldTemplateImportListener;
import com.beanframework.console.listener.EmployeeImportListener;
import com.beanframework.console.listener.EnumerationImportListener;
import com.beanframework.console.listener.LanguageImportListener;
import com.beanframework.console.listener.MediaImportListener;
import com.beanframework.console.listener.MenuImportListener;
import com.beanframework.console.listener.SiteImportListener;
import com.beanframework.console.listener.UserAuthorityImportListener;
import com.beanframework.console.listener.UserGroupImportListener;
import com.beanframework.console.listener.UserPermissionImportListener;
import com.beanframework.console.listener.UserRightImportListener;
import com.beanframework.console.registry.ImportListenerRegistry;

@Configuration
public class ConsoleImportListenerConfig implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private ImportListenerRegistry importListenerRegistry;

	@Value("#{'${module.console.import.listener.keys}'.split(',')}")
	private List<String> importListenerKeyList;

	@Bean
	public AdminImportListener adminImportListener() {
		return new AdminImportListener();
	}

	@Bean
	public ConfigurationImportListener configurationImportListener() {
		return new ConfigurationImportListener();
	}
	
	@Bean
	public EnumerationImportListener enumerationImportListener() {
		return new EnumerationImportListener();
	}

	@Bean
	public DynamicFieldImportListener dynamicFieldImportListener() {
		return new DynamicFieldImportListener();
	}
	
	@Bean
	public DynamicFieldTemplateImportListener dynamicFieldTemplateImportListener() {
		return new DynamicFieldTemplateImportListener();
	}

	@Bean
	public EmployeeImportListener employeeImportListener() {
		return new EmployeeImportListener();
	}

	@Bean
	public LanguageImportListener languageImportListener() {
		return new LanguageImportListener();
	}

	@Bean
	public MenuImportListener menuImportListener() {
		return new MenuImportListener();
	}

	@Bean
	public UserPermissionImportListener userPermissionImportListener() {
		return new UserPermissionImportListener();
	}

	@Bean
	public UserGroupImportListener userGroupImportListener() {
		return new UserGroupImportListener();
	}

	@Bean
	public UserRightImportListener userRightImportListener() {
		return new UserRightImportListener();
	}

	@Bean
	public UserAuthorityImportListener userAuthorityImportListener() {
		return new UserAuthorityImportListener();
	}

	@Bean
	public CustomerImportListener customerImportListener() {
		return new CustomerImportListener();
	}

	@Bean
	public CronjobImportListener cronjobImportListener() {
		return new CronjobImportListener();
	}
	
	@Bean
	public SiteImportListener siteImportListener() {
		return new SiteImportListener();
	}
	
	@Bean
	public MediaImportListener mediaImportListener() {
		return new MediaImportListener();
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.AdminImport.KEY))
			importListenerRegistry.addListener(adminImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.ConfigurationImport.KEY))
			importListenerRegistry.addListener(configurationImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.CronjobImport.KEY))
			importListenerRegistry.addListener(cronjobImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.CustomerImport.KEY))
			importListenerRegistry.addListener(customerImportListener());
		
		if (importListenerKeyList.contains(ConsoleImportListenerConstants.EnumerationImport.KEY))
			importListenerRegistry.addListener(enumerationImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.DynamicFieldImport.KEY))
			importListenerRegistry.addListener(dynamicFieldImportListener());
		
		if (importListenerKeyList.contains(ConsoleImportListenerConstants.DynamicFieldTemplateImport.KEY))
			importListenerRegistry.addListener(dynamicFieldTemplateImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.EmployeeImport.KEY))
			importListenerRegistry.addListener(employeeImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.LanguageImport.KEY))
			importListenerRegistry.addListener(languageImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.MenuImport.KEY))
			importListenerRegistry.addListener(menuImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.UserAuthorityImport.KEY))
			importListenerRegistry.addListener(userAuthorityImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.UserGroupImport.KEY))
			importListenerRegistry.addListener(userGroupImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.UserPermissionImport.KEY))
			importListenerRegistry.addListener(userPermissionImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.UserRightImport.KEY))
			importListenerRegistry.addListener(userRightImportListener());
		
		if (importListenerKeyList.contains(ConsoleImportListenerConstants.SiteImport.KEY))
			importListenerRegistry.addListener(siteImportListener());
		
		if (importListenerKeyList.contains(ConsoleImportListenerConstants.MediaImport.KEY))
			importListenerRegistry.addListener(mediaImportListener());
	}
}