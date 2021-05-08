package com.beanframework.trainingcore.converter.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.training.domain.Training;
import com.beanframework.trainingcore.data.TrainingDto;

public class EntityTrainingConverter implements EntityConverter<TrainingDto, Training> {

	@Autowired
	private ModelService modelService;

	@Override
	public Training convert(TrainingDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Training prototype = modelService.findOneByUuid(source.getUuid(), Training.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Training.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Training convertToEntity(TrainingDto source, Training prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
