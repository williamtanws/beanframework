package com.beanframework.dynamicfield.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;

@Component
public class EntityDynamicFieldConverter implements EntityConverter<DynamicField, DynamicField> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField convert(DynamicField source) {

		DynamicField prototype = modelService.create(DynamicField.class);
		if (source.getUuid() != null) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(DynamicField.UUID, source.getUuid());
			DynamicField exists = modelService.findOneEntityByProperties(properties, DynamicField.class);
			
			if (exists != null) {
				prototype = exists;
			}
		}

		return convert(source, prototype);
	}

	private DynamicField convert(DynamicField source, DynamicField prototype) {

		prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());
		
		prototype.setRequired(source.getRequired());
		prototype.setRule(source.getRule());
		prototype.setSort(source.getSort());
		prototype.setType(source.getType());

		return prototype;
	}

}
