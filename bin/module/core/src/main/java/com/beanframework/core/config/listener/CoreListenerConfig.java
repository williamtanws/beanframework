package com.beanframework.core.config.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.registry.AfterRemoveListenerRegistry;
import com.beanframework.common.registry.AfterSaveListenerRegistry;
import com.beanframework.common.registry.BeforeRemoveListenerRegistry;
import com.beanframework.common.registry.BeforeSaveListenerRegistry;
import com.beanframework.core.listener.CoreAfterRemoveListener;
import com.beanframework.core.listener.CoreAfterSaveListener;
import com.beanframework.core.listener.CoreBeforeRemoveListener;
import com.beanframework.core.listener.CoreBeforeSaveListener;

@Configuration
public class CoreListenerConfig implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private BeforeSaveListenerRegistry beforeSaveListenerRegistry;
	
	@Autowired
	private AfterSaveListenerRegistry afterSaveListenerRegistry;

	@Autowired
	private BeforeRemoveListenerRegistry beforeRemoveListenerRegistry;
	
	@Autowired
	private AfterRemoveListenerRegistry afterRemoveListenerRegistry;

	@Bean
	public CoreBeforeSaveListener coreBeforeSaveListener() {
		return new CoreBeforeSaveListener();
	}
	
	@Bean
	public CoreAfterSaveListener coreAfterSaveListener() {
		return new CoreAfterSaveListener();
	}

	@Bean
	public CoreBeforeRemoveListener coreBeforeRemoveListener() {
		return new CoreBeforeRemoveListener();
	}
	
	@Bean
	public CoreAfterRemoveListener coreAfterRemoveListener() {
		return new CoreAfterRemoveListener();
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		beforeSaveListenerRegistry.addListener(coreBeforeSaveListener());
		afterSaveListenerRegistry.addListener(coreAfterSaveListener());
		beforeRemoveListenerRegistry.addListener(coreBeforeRemoveListener());
		afterRemoveListenerRegistry.addListener(coreAfterRemoveListener());
	}
}
