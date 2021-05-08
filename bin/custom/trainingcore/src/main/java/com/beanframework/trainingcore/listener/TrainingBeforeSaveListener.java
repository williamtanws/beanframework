package com.beanframework.trainingcore.listener;

import org.springframework.stereotype.Component;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.BeforeSaveEvent;
import com.beanframework.common.registry.BeforeSaveListener;
import com.beanframework.training.domain.Training;

@Component
public class TrainingBeforeSaveListener implements BeforeSaveListener {

	@Override
	public void beforeSave(Object model, BeforeSaveEvent event) throws ListenerException {
		try {
			if (model instanceof Training) {

			}

		} catch (Exception e) {
			throw new ListenerException(e.getMessage(), e);
		}
	}

}
