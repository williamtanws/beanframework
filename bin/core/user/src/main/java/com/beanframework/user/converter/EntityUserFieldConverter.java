package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserField;

public class EntityUserFieldConverter implements EntityConverter<UserField, UserField> {

	@Autowired
	private ModelService modelService;

	public List<UserField> convert(List<UserField> sources) throws ConverterException {
		List<UserField> convertedList = new ArrayList<UserField>();
		for (UserField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	@Override
	public UserField convert(UserField source) throws ConverterException {

		UserField prototype;
		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserField.UUID, source.getUuid());

				UserField exists = modelService.findOneEntityByProperties(properties, UserField.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(UserField.class);
				}
			} else {
				prototype = modelService.create(UserField.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private UserField convert(UserField source, UserField prototype) throws ConverterException {

		try {
			if (source.getId() != null)
				prototype.setId(source.getId());
			prototype.setLastModifiedDate(new Date());

			if (source.getDynamicField() == null) {
				prototype.setDynamicField(null);
			} else {
				DynamicField dynamicField = modelService.findOneEntityByUuid(source.getDynamicField().getUuid(),
						DynamicField.class);
				prototype.setDynamicField(dynamicField);
			}
			prototype.setValue(source.getValue());
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
