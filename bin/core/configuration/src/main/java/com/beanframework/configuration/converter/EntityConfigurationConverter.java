package com.beanframework.configuration.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;

public class EntityConfigurationConverter implements EntityConverter<Configuration, Configuration> {

	@Autowired
	private ModelService modelService;

	@Override
	public Configuration convert(Configuration source) throws ConverterException {

		Configuration prototype;
		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Configuration.UUID, source.getUuid());
				Configuration exists = modelService.findOneEntityByProperties(properties, Configuration.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(Configuration.class);
				}
			} else {
				prototype = modelService.create(Configuration.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private Configuration convert(Configuration source, Configuration prototype) {

		prototype.setLastModifiedDate(new Date());

		if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
			prototype.setId(StringUtils.strip(source.getId()));

		if (StringUtils.equals(source.getValue(), prototype.getValue()) == false)
			prototype.setValue(StringUtils.strip(source.getValue()));

		return prototype;
	}

}
