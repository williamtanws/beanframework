package com.beanframework.core.listener;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterRemoveEvent;
import com.beanframework.common.registry.AfterRemoveListener;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.menu.domain.Menu;

public class CoreAfterRemoveListener implements AfterRemoveListener {

	@Override
	public void afterRemove(Object detachedModel, AfterRemoveEvent event) throws ListenerException {
		try {
			if (detachedModel instanceof Menu) {

			} else if (detachedModel instanceof Configuration) {

			}
		} catch (Exception e) {
			throw new ListenerException(e.getMessage(), e);
		}
	}
}
