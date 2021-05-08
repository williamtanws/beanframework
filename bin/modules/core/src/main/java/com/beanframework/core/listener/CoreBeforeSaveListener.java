package com.beanframework.core.listener;

import org.springframework.stereotype.Component;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.BeforeSaveEvent;
import com.beanframework.common.registry.BeforeSaveListener;

@Component
public class CoreBeforeSaveListener implements BeforeSaveListener {

	@Override
	public void beforeSave(Object model, BeforeSaveEvent event) throws ListenerException {		
	}

}
