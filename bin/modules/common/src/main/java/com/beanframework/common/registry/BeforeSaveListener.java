package com.beanframework.common.registry;

import com.beanframework.common.exception.ListenerException;

public abstract interface BeforeSaveListener {
	public abstract void beforeSave(final Object model, final BeforeSaveEvent event) throws ListenerException;
}
