package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightFieldDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class EntityUserRightConverter implements EntityConverter<UserRightDto, UserRight> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserRight convert(UserRightDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRight.UUID, source.getUuid());

				UserRight prototype = modelService.findOneByProperties(properties, UserRight.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convert(source, modelService.create(UserRight.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private UserRight convertToEntity(UserRightDto source, UserRight prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getSort() == null) {
				if (prototype.getSort() != null) {
					prototype.setSort(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getSort() == null || prototype.getSort() == source.getSort() == Boolean.FALSE) {
					prototype.setSort(source.getSort());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// Field
			if (source.getFields() == null || source.getFields().isEmpty()) {
				if (prototype.getFields().isEmpty() == Boolean.FALSE) {
					prototype.setFields(new ArrayList<UserRightField>());
				}
			} else {
				// Update
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserRightFieldDto sourceField : source.getFields()) {
						if (prototype.getFields().get(i).getDynamicFieldSlot().getUuid().equals(sourceField.getDynamicFieldSlot().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == Boolean.FALSE) {
								prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}

				// Add
				for (UserRightFieldDto sourceField : source.getFields()) {
					if (sourceField.getDynamicFieldSlot().getUuid() == null && StringUtils.isNotBlank(sourceField.getDynamicFieldSlot().getId())) {
						Map<String, Object> properties = new HashMap<String, Object>();
						properties.put(DynamicField.ID, sourceField.getDynamicFieldSlot().getId());
						DynamicFieldSlot entityDynamicFieldSlot = modelService.findOneByProperties(properties, DynamicFieldSlot.class);

						UserRightField field = new UserRightField();
						field.setUserRight(prototype);
						field.setDynamicFieldSlot(entityDynamicFieldSlot);
						field.setValue(StringUtils.stripToNull(sourceField.getValue()));

						prototype.getFields().add(field);
					}
				}
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
