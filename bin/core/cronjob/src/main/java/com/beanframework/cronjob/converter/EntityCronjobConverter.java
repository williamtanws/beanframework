package com.beanframework.cronjob.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Cronjob.UUID, source.getUuid());

				Cronjob prototype = modelService.findOneEntityByProperties(properties, Cronjob.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Cronjob.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

	}

	private Cronjob convert(Cronjob source, Cronjob prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false) {
			prototype.setId(StringUtils.strip(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getJobClass(), prototype.getJobClass()) == false) {
			prototype.setJobClass(StringUtils.strip(source.getJobClass()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getJobGroup(), prototype.getJobGroup()) == false) {
			prototype.setJobGroup(StringUtils.strip(source.getJobGroup()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getJobName(), prototype.getJobName()) == false) {
			prototype.setJobName(StringUtils.strip(source.getJobName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getDescription(), prototype.getDescription()) == false) {
			prototype.setDescription(StringUtils.strip(source.getDescription()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getCronExpression(), prototype.getCronExpression()) == false) {
			prototype.setCronExpression(StringUtils.strip(source.getCronExpression()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getStartup() != prototype.getStartup()) {
			prototype.setStartup(source.getStartup());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
