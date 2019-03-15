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

				UserRight prototype = modelService.findOneEntityByProperties(properties, true, UserRight.class);

				if (prototype != null) {
					return convertDto(source, prototype);
				}
			}

			return convert(source, modelService.create(UserRight.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private UserRight convertDto(UserRightDto source, UserRight prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getSort() == null) {
				if (prototype.getSort() != null) {
					prototype.setSort(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getSort() == null || prototype.getSort() == source.getSort() == false) {
					prototype.setSort(source.getSort());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// Field
			if (source.getFields() == null || source.getFields().isEmpty()) {
				if (prototype.getFields().isEmpty() == false) {
					prototype.setFields(new ArrayList<UserRightField>());
				}
			} else {
				// Update
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserRightFieldDto sourceField : source.getFields()) {
						if (prototype.getFields().get(i).getDynamicField().getUuid().equals(sourceField.getDynamicField().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == false) {
								prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
							if (sourceField.getSort() == prototype.getFields().get(i).getSort() == false) {
								prototype.getFields().get(i).setSort(sourceField.getSort());

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}

				// Add
				for (UserRightFieldDto sourceField : source.getFields()) {
					if (sourceField.getDynamicField().getUuid() == null && StringUtils.isNotBlank(sourceField.getDynamicField().getId())) {
						Map<String, Object> properties = new HashMap<String, Object>();
						properties.put(DynamicField.ID, sourceField.getDynamicField().getId());
						DynamicField entityDynamicField = modelService.findOneEntityByProperties(properties, false, DynamicField.class);

						UserRightField field = new UserRightField();
						field.setUserRight(prototype);
						field.setDynamicField(entityDynamicField);
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
