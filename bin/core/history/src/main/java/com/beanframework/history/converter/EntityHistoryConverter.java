package com.beanframework.history.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.history.domain.History;

@Component
public class EntityHistoryConverter implements DtoConverter<History, History> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public History convert(History source) {

		History prototype = modelService.create(History.class);
		if (source.getUuid() != null) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(History.UUID, source.getUuid());
			History exists = modelService.findOneEntityByProperties(properties, History.class);
			
			if (exists != null) {
				prototype = exists;
			}
		}

		return convert(source, prototype);
	}

	private History convert(History source, History prototype) {

		prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}
