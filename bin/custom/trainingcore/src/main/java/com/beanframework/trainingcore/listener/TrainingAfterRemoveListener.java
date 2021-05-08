package com.beanframework.trainingcore.listener;

import org.springframework.stereotype.Component;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterRemoveEvent;
import com.beanframework.common.registry.AfterRemoveListener;
import com.beanframework.training.domain.Training;

@Component
public class TrainingAfterRemoveListener implements AfterRemoveListener {

	@Override
	public void afterRemove(Object detachedModel, AfterRemoveEvent event) throws ListenerException {
		try {
			if (detachedModel instanceof Training) {

			}
		} catch (Exception e) {
			throw new ListenerException(e.getMessage(), e);
		}
	}
}
