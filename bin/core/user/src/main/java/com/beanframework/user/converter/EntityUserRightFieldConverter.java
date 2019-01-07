package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserRightField;

public class EntityUserRightFieldConverter implements EntityConverter<UserRightField, UserRightField> {

	@Autowired
	private ModelService modelService;

	public List<UserRightField> convert(List<UserRightField> sources) throws ConverterException {
		List<UserRightField> convertedList = new ArrayList<UserRightField>();
		for (UserRightField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	@Override
	public UserRightField convert(UserRightField source) throws ConverterException {

		UserRightField prototype;
		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRightField.UUID, source.getUuid());

				UserRightField exists = modelService.findOneEntityByProperties(properties, UserRightField.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(UserRightField.class);
				}
			} else {
				prototype = modelService.create(UserRightField.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private UserRightField convert(UserRightField source, UserRightField prototype) throws ConverterException {

		try {
			prototype.setLastModifiedDate(new Date());

			if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
				prototype.setId(StringUtils.strip(source.getId()));

			// Dynamic Field
			if (source.getDynamicField() == null) {
				if (prototype.getDynamicField() != null)
					prototype.setDynamicField(null);
			} else {
				boolean add = true;
				if (prototype.getDynamicField() != null) {
					if (source.getDynamicField().getUuid() == prototype.getDynamicField().getUuid()) {
						add = false;
					}
				}

				if (add) {
					DynamicField dynamicField = modelService.findOneEntityByUuid(source.getDynamicField().getUuid(), DynamicField.class);
					prototype.setDynamicField(dynamicField);
				}
			}

			if (StringUtils.equals(prototype.getValue(), source.getValue()) == false)
				prototype.setValue(StringUtils.strip(source.getValue()));
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
