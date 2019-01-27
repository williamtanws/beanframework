package com.beanframework.common.registry;

import com.beanframework.common.exception.ListenerException;

public abstract interface AfterSaveListener {
	public abstract void afterSave(Object model, AfterSaveEvent event) throws ListenerException;
}
