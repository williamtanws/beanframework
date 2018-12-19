package com.beanframework.cronjob.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;

public class EntityCronjobConverter implements EntityConverter<Cronjob, Cronjob> {

	@Autowired
	private ModelService modelService;

	@Override
	public Cronjob convert(Cronjob source) throws ConverterException {

		Cronjob prototype;
		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Cronjob.UUID, source.getUuid());

				Cronjob exists = modelService.findOneEntityByProperties(properties, Cronjob.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(Cronjob.class);
				}
			} else {
				prototype = modelService.create(Cronjob.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private Cronjob convert(Cronjob source, Cronjob prototype) {

		if (source.getId() != null) {
			prototype.setId(source.getId());
		}
		prototype.setJobClass(source.getJobClass());
		prototype.setJobGroup(source.getJobGroup());
		prototype.setJobName(source.getJobName());
		prototype.setDescription(source.getDescription());
		prototype.setCronExpression(source.getCronExpression());
		prototype.setStartup(source.isStartup());
		prototype.setLastModifiedDate(new Date());

		prototype.getCronjobDatas().clear();
		for (int i = 0; i < source.getCronjobDatas().size(); i++) {
			source.getCronjobDatas().get(i).setCronjob(prototype);
		}
		prototype.getCronjobDatas().addAll(source.getCronjobDatas());

		return prototype;
	}

}
