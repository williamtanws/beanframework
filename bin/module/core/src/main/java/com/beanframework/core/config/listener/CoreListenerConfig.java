package com.beanframework.core.config.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.registry.AfterSaveListenerRegistry;
import com.beanframework.common.registry.BeforeRemoveListenerRegistry;
import com.beanframework.core.listener.CoreAfterSaveListener;
import com.beanframework.core.listener.CoreBeforeRemoveListener;

@Configuration
public class CoreListenerConfig implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private AfterSaveListenerRegistry afterSaveListenerRegistry;

	@Autowired
	private BeforeRemoveListenerRegistry beforeRemoveListenerRegistry;

	@Bean
	public CoreAfterSaveListener coreAfterSaveListener() {
		return new CoreAfterSaveListener();
	}

	@Bean
	public CoreBeforeRemoveListener coreBeforeRemoveListener() {
		return new CoreBeforeRemoveListener();
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		afterSaveListenerRegistry.addListener(coreAfterSaveListener());
		beforeRemoveListenerRegistry.addListener(coreBeforeRemoveListener());
	}
}