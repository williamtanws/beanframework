package com.beanframework.core.converter.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightAttributeDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightAttribute;

@Component
public class UserRightEntityConverter implements EntityConverter<UserRightDto, UserRight> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserRight convert(UserRightDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				UserRight prototype = modelService.findOneByUuid(source.getUuid(), UserRight.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(UserRight.class));

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

			// Attribute
			if (source.getAttributes() == null || source.getAttributes().isEmpty()) {
				if (prototype.getAttributes().isEmpty() == Boolean.FALSE) {
					prototype.setAttributes(new ArrayList<UserRightAttribute>());
				}
			} else {
				// Update
				for (int i = 0; i < prototype.getAttributes().size(); i++) {
					for (UserRightAttributeDto sourceField : source.getAttributes()) {
						if (prototype.getAttributes().get(i).getDynamicFieldSlot().equals(sourceField.getDynamicFieldSlot().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getAttributes().get(i).getValue()) == Boolean.FALSE) {
								prototype.getAttributes().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getAttributes().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}

				// Add
				for (UserRightAttributeDto sourceField : source.getAttributes()) {
					if (sourceField.getDynamicFieldSlot().getUuid() == null && StringUtils.isNotBlank(sourceField.getDynamicFieldSlot().getId())) {
						Map<String, Object> properties = new HashMap<String, Object>();
						properties.put(DynamicField.ID, sourceField.getDynamicFieldSlot().getId());
						DynamicFieldSlot entityDynamicFieldSlot = modelService.findOneByProperties(properties, DynamicFieldSlot.class);

						UserRightAttribute field = new UserRightAttribute();
						field.setUserRight(prototype);
						field.setDynamicFieldSlot(entityDynamicFieldSlot.getUuid());
						field.setValue(StringUtils.stripToNull(sourceField.getValue()));

						prototype.getAttributes().add(field);
					}
				}
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
