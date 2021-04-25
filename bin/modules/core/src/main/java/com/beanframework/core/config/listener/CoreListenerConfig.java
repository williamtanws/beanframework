package com.beanframework.core.config.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.core.listener.CoreAfterRemoveListener;
import com.beanframework.core.listener.CoreAfterSaveListener;
import com.beanframework.core.listener.CoreBeforeRemoveListener;
import com.beanframework.core.listener.CoreBeforeSaveListener;

@Configuration
public class CoreListenerConfig {

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
}
