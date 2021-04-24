package com.beanframework.common.registry;

import com.beanframework.common.exception.ListenerException;

public abstract interface AfterSaveListener {
	public abstract void afterSave(final Object model, final AfterSaveEvent event) throws ListenerException;
}
