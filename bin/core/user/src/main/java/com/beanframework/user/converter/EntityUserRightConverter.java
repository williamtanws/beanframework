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
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class EntityUserRightConverter implements EntityConverter<UserRight, UserRight> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserRight convert(UserRight source) throws ConverterException {

		UserRight prototype;
		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRight.UUID, source.getUuid());

				UserRight exists = modelService.findOneEntityByProperties(properties, UserRight.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(UserRight.class);
				}
			} else {
				prototype = modelService.create(UserRight.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private UserRight convert(UserRight source, UserRight prototype) throws ConverterException {

		try {
			if (source.getId() != null)
				prototype.setId(source.getId());
			prototype.setLastModifiedDate(new Date());

			prototype.setSort(source.getSort());
			if (source.getUserRightFields() == null || source.getUserRightFields().isEmpty()) {
				prototype.setUserRightFields(new ArrayList<UserRightField>());
			} else {
				List<UserRightField> userRightFields = new ArrayList<UserRightField>();
				for (UserRightField userRightField : source.getUserRightFields()) {
					UserRightField entityUserRightField = modelService.findOneEntityByUuid(userRightField.getUuid(),
							UserRightField.class);
					entityUserRightField.setValue(userRightField.getValue());
					userRightFields.add(entityUserRightField);
				}
				prototype.setUserRightFields(userRightFields);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
