package com.beanframework.console.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	private UpdaterRegistry updaterRegistry;
	
	@Autowired
	private RemoverRegistry removerRegistry;

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