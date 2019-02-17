package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.console.csv.ConfigurationCsv;

@Component
public class EntityCsvConfigurationConverter implements EntityConverter<ConfigurationCsv, Configuration> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvConfigurationConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Configuration convert(ConfigurationCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Configuration.ID, source.getId());

				Configuration prototype = modelService.findOneEntityByProperties(properties, true, Configuration.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, new Configuration());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public Configuration convert(ConfigurationCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private Configuration convert(ConfigurationCsv source, Configuration prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setValue(StringUtils.stripToNull(source.getValue()));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
