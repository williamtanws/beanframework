package com.beanframework.trainingcore.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.training.domain.Training;
import com.beanframework.trainingcore.data.TrainingDto;


public class TrainingPopulator extends AbstractPopulator<Training, TrainingDto> implements Populator<Training, TrainingDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(TrainingPopulator.class);

	@Override
	public void populate(Training source, TrainingDto target) throws PopulatorException {
		populateGeneric(source, target);
		target.setName(source.getName());
	}

}
