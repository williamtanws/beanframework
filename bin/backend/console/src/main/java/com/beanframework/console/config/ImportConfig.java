package com.beanframework.console.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.console.initialize.CronjobInitialize;
import com.beanframework.console.initialize.CustomerInitialize;
import com.beanframework.console.initialize.EmployeeInitialize;
import com.beanframework.console.initialize.LanguageInitialize;
import com.beanframework.console.initialize.MenuInitialize;
import com.beanframework.console.initialize.UserGroupInitialize;
import com.beanframework.console.initialize.UserPermissionInitialize;
import com.beanframework.console.initialize.UserRightInitialize;
import com.beanframework.console.registry.InitializerRegistry;
import com.beanframework.console.registry.RemoverRegistry;
import com.beanframework.console.registry.UpdaterRegistry;
import com.beanframework.console.remove.CustomerRemove;
import com.beanframework.console.update.CronjobUpdate;
import com.beanframework.console.update.CustomerUpdate;
import com.beanframework.console.update.EmployeeUpdate;
import com.beanframework.console.update.LanguageUpdate;
import com.beanframework.console.update.MenuUpdate;
import com.beanframework.console.update.UserAuthorityUpdate;
import com.beanframework.console.update.UserGroupUpdate;
import com.beanframework.console.update.UserPermissionUpdate;
import com.beanframework.console.update.UserRightUpdate;

@Configuration
public class ImportConfig implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private InitializerRegistry initializerRegistry;

	@Autowired
	private UpdaterRegistry updaterRegistry;
	
	@Autowired
	private RemoverRegistry removerRegistry;

	@Bean
	public EmployeeInitialize employeeInitialize() {
		return new EmployeeInitialize();
	}

	@Bean
	public LanguageInitialize languageInitialize() {
		return new LanguageInitialize();
	}

	@Bean
	public MenuInitialize menuInitialize() {
		return new MenuInitialize();
	}

	@Bean
	public UserPermissionInitialize permissionInitialize() {
		return new UserPermissionInitialize();
	}

	@Bean
	public UserGroupInitialize userGroupInitialize() {
		return new UserGroupInitialize();
	}

	@Bean
	public UserRightInitialize userRightInitialize() {
		return new UserRightInitialize();
	}
	
	@Bean
	public CustomerInitialize customerInitialize() {
		return new CustomerInitialize();
	}
	
	@Bean
	public CronjobInitialize cronjobInitialize() {
		return new CronjobInitialize();
	}

	@Bean
	public EmployeeUpdate employeeUpdate() {
		return new EmployeeUpdate();
	}

	@Bean
	public LanguageUpdate languageUpdate() {
		return new LanguageUpdate();
	}

	@Bean
	public MenuUpdate menuUpdate() {
		return new MenuUpdate();
	}

	@Bean
	public UserPermissionUpdate permissionUpdate() {
		return new UserPermissionUpdate();
	}

	@Bean
	public UserGroupUpdate userGroupUpdate() {
		return new UserGroupUpdate();
	}

	@Bean
	public UserRightUpdate userRightUpdate() {
		return new UserRightUpdate();
	}

	@Bean
	public UserAuthorityUpdate userAuthorityUpdate() {
		return new UserAuthorityUpdate();
	}
	
	@Bean
	public CustomerUpdate customerUpdate() {
		return new CustomerUpdate();
	}
	
	@Bean
	public CronjobUpdate cronjobUpdate() {
		return new CronjobUpdate();
	}
	
	@Bean
	public CustomerRemove customerRemove() {
		return new CustomerRemove();
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		initializerRegistry.addInitializer(languageInitialize());
		initializerRegistry.addInitializer(userRightInitialize());
		initializerRegistry.addInitializer(permissionInitialize());
		initializerRegistry.addInitializer(userGroupInitialize());
		initializerRegistry.addInitializer(menuInitialize());
		initializerRegistry.addInitializer(employeeInitialize());
		initializerRegistry.addInitializer(customerInitialize());
		initializerRegistry.addInitializer(cronjobInitialize());

		updaterRegistry.addUpdater(languageUpdate());
		updaterRegistry.addUpdater(userRightUpdate());
		updaterRegistry.addUpdater(permissionUpdate());
		updaterRegistry.addUpdater(userGroupUpdate());
		updaterRegistry.addUpdater(userAuthorityUpdate());
		updaterRegistry.addUpdater(menuUpdate());
		updaterRegistry.addUpdater(employeeUpdate());
		updaterRegistry.addUpdater(customerUpdate());
		updaterRegistry.addUpdater(cronjobUpdate());
		
		removerRegistry.addRemover(customerRemove());
	}
}