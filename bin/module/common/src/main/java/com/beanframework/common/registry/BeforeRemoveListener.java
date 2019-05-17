package com.beanframework.common.registry;

import com.beanframework.common.exception.ListenerException;

public abstract interface BeforeRemoveListener {
	public abstract void beforeRemove(final Object model, final BeforeRemoveEvent event) throws ListenerException;
}
