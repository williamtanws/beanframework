package com.beanframework.dynamicfield.converter;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.service.DynamicFieldService;

@Component
public class EntityDynamicFieldConverter implements EntityConverter<DynamicField, DynamicField> {

	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Override
	public DynamicField convert(DynamicField source) {

		Optional<DynamicField> prototype = null;
		if (source.getUuid() != null) {
			prototype = dynamicFieldService.findEntityByUuid(source.getUuid());
			if (prototype.isPresent() == false) {
				prototype = Optional.of(dynamicFieldService.create());
			}
		}
		else {
			prototype = Optional.of(dynamicFieldService.create());
		}

		return convert(source, prototype.get());
	}

	private DynamicField convert(DynamicField source, DynamicField prototype) {

		prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}
