
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
import com.beanframework.console.listener.DynamicFieldEnumImportListener;
import com.beanframework.console.listener.DynamicFieldImportListener;
import com.beanframework.console.listener.EmployeeImportListener;
import com.beanframework.console.listener.LanguageImportListener;
import com.beanframework.console.listener.MenuImportListener;
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
	public DynamicFieldEnumImportListener dynamicFieldEnumImportListener() {
		return new DynamicFieldEnumImportListener();
	}

	@Bean
	public DynamicFieldImportListener dynamicFieldImportListener() {
		return new DynamicFieldImportListener();
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

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.AdminImportListener.KEY))
			importListenerRegistry.addListener(adminImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.ConfigurationImportListener.KEY))
			importListenerRegistry.addListener(configurationImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.CronjobImportListener.KEY))
			importListenerRegistry.addListener(cronjobImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.CustomerImportListener.KEY))
			importListenerRegistry.addListener(customerImportListener());
		
		if (importListenerKeyList.contains(ConsoleImportListenerConstants.DynamicFieldEnumImportListener.KEY))
			importListenerRegistry.addListener(dynamicFieldEnumImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.DynamicFieldImportListener.KEY))
			importListenerRegistry.addListener(dynamicFieldImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.EmployeeImportListener.KEY))
			importListenerRegistry.addListener(employeeImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.LanguageImportListener.KEY))
			importListenerRegistry.addListener(languageImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.MenuImportListener.KEY))
			importListenerRegistry.addListener(menuImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.UserAuthorityImportListener.KEY))
			importListenerRegistry.addListener(userAuthorityImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.UserGroupImportListener.KEY))
			importListenerRegistry.addListener(userGroupImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.UserPermissionImportListener.KEY))
			importListenerRegistry.addListener(userPermissionImportListener());

		if (importListenerKeyList.contains(ConsoleImportListenerConstants.UserRightImportListener.KEY))
			importListenerRegistry.addListener(userRightImportListener());
	}
}