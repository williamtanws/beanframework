package com.beanframework.trainingcore.listener;

import org.springframework.stereotype.Component;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.BeforeRemoveEvent;
import com.beanframework.common.registry.BeforeRemoveListener;
import com.beanframework.training.domain.Training;

@Component
public class TrainingBeforeRemoveListener implements BeforeRemoveListener {

	@Override
	public void beforeRemove(final Object model, final BeforeRemoveEvent event) throws ListenerException {

		try {
			if (model instanceof Training) {

			}

		} catch (Exception e) {
			throw new ListenerException(e.getMessage(), e);
		}
	}
}
