package com.beanframework.trainingcore.converter.entity.csv;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.training.domain.Training;
import com.beanframework.trainingcore.csv.TrainingCsv;


public class EntityCsvTrainingConverter implements EntityCsvConverter<TrainingCsv, Training> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvTrainingConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Training convert(TrainingCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Training.ID, source.getId());

				Training prototype = modelService.findOneByProperties(properties, Training.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Training.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Training convertToEntity(TrainingCsv source, Training prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
