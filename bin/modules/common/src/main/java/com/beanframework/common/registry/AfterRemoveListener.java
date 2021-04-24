package com.beanframework.common.registry;

import com.beanframework.common.exception.ListenerException;

public abstract interface AfterRemoveListener {
	public abstract void afterRemove(final Object detachedModel, final AfterRemoveEvent event) throws ListenerException;
}
