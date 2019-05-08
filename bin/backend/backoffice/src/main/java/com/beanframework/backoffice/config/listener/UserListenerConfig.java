package com.beanframework.backoffice.config.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.registry.AfterSaveListenerRegistry;
import com.beanframework.user.listener.UserAfterSaveListener;

@Configuration
public class UserListenerConfig implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private AfterSaveListenerRegistry afterSaveListenerRegistry;

	@Bean
	public UserAfterSaveListener userAfterSaveListener() {
		return new UserAfterSaveListener();
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		afterSaveListenerRegistry.addListener(userAfterSaveListener());
	}
}
