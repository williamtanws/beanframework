package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.ConfigurationDto;

public class EntityConfigurationConverter implements EntityConverter<ConfigurationDto, Configuration> {

	@Autowired
	private ModelService modelService;

	@Override
	public Configuration convert(ConfigurationDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Configuration.UUID, source.getUuid());
				Configuration prototype = modelService.findOneByProperties(properties, Configuration.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Configuration.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private Configuration convertToEntity(ConfigurationDto source, Configuration prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getValue()), prototype.getValue()) == Boolean.FALSE) {
			prototype.setValue(StringUtils.stripToNull(source.getValue()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
