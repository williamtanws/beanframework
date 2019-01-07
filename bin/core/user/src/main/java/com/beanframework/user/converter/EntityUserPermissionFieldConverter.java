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
import com.beanframework.user.domain.UserPermissionField;

public class EntityUserPermissionFieldConverter implements EntityConverter<UserPermissionField, UserPermissionField> {

	@Autowired
	private ModelService modelService;

	public List<UserPermissionField> convert(List<UserPermissionField> sources) throws ConverterException {
		List<UserPermissionField> convertedList = new ArrayList<UserPermissionField>();
		for (UserPermissionField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	@Override
	public UserPermissionField convert(UserPermissionField source) throws ConverterException {

		UserPermissionField prototype;
		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserPermissionField.UUID, source.getUuid());

				UserPermissionField exists = modelService.findOneEntityByProperties(properties, UserPermissionField.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(UserPermissionField.class);
				}
			} else {
				prototype = modelService.create(UserPermissionField.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private UserPermissionField convert(UserPermissionField source, UserPermissionField prototype) throws ConverterException {

		try {
			prototype.setLastModifiedDate(new Date());

			if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
				prototype.setId(source.getId());

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

			if (StringUtils.equals(source.getValue(), prototype.getValue()) == false)
				prototype.setValue(source.getValue());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
