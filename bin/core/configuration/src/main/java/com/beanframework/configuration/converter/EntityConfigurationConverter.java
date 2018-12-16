package com.beanframework.configuration.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;

public class EntityConfigurationConverter implements EntityConverter<Configuration, Configuration> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public Configuration convert(Configuration source) {

		Configuration prototype = null;
		if (source.getUuid() != null) {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Configuration.UUID, source.getUuid());
			
			prototype = modelService.findOneEntityByProperties(properties, Configuration.class);
			
			if (prototype == null) {
				prototype = modelService.create(Configuration.class);
			}
		}
		else {
			prototype = modelService.create(Configuration.class);
		}

		return convert(source, prototype);
	}

	private Configuration convert(Configuration source, Configuration prototype) {

		prototype.setId(source.getId());
		prototype.setValue(source.getValue());
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}
