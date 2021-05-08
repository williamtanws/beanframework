package com.beanframework.trainingcore.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterSaveEvent;
import com.beanframework.common.registry.AfterSaveListener;
import com.beanframework.training.domain.Training;

@Component
public class TrainingAfterSaveListener implements AfterSaveListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(TrainingAfterSaveListener.class);

	@Override
	public void afterSave(final Object model, final AfterSaveEvent event) throws ListenerException {

		try {
			if (model instanceof Training) {

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ListenerException(e.getMessage(), e);
		}

	}
}
