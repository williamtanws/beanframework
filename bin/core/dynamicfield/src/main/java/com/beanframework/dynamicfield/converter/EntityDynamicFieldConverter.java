package com.beanframework.dynamicfield.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;

@Component
public class EntityDynamicFieldConverter implements EntityConverter<DynamicField, DynamicField> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField convert(DynamicField source) throws ConverterException {

		DynamicField prototype;
		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicField.UUID, source.getUuid());
				DynamicField exists = modelService.findOneEntityByProperties(properties, DynamicField.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(DynamicField.class);
				}
			} else {
				prototype = modelService.create(DynamicField.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private DynamicField convert(DynamicField source, DynamicField prototype) {

		if (source.getId() != null)
			prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		if (source.getName() != null)
			prototype.setName(source.getName());
		if (source.getRequired() != null)
			prototype.setRequired(source.getRequired());
		if (source.getRule() != null)
			prototype.setRule(source.getRule());
		if (source.getSort() != null)
			prototype.setSort(source.getSort());
		if (source.getType() != null)
			prototype.setType(source.getType());

		return prototype;
	}

}
