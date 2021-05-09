package com.beanframework.core.converter.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionAttributeDto;
import com.beanframework.user.domain.UserPermission;

@Component
public class UserPermissionEntityConverter implements EntityConverter<UserPermissionDto, UserPermission> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermission convert(UserPermissionDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				UserPermission prototype = modelService.findOneByUuid(source.getUuid(), UserPermission.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(UserPermission.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private UserPermission convertToEntity(UserPermissionDto source, UserPermission prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(source.getName(), prototype.getName()) == Boolean.FALSE) {
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
			if (source.getAttributes() != null && source.getAttributes().isEmpty() == Boolean.FALSE) {
				for (int i = 0; i < prototype.getAttributes().size(); i++) {
					for (UserPermissionAttributeDto sourceField : source.getAttributes()) {

						if (prototype.getAttributes().get(i).getDynamicFieldSlot().equals(sourceField.getDynamicFieldSlot().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getAttributes().get(i).getValue()) == Boolean.FALSE) {
								prototype.getAttributes().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getAttributes().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
