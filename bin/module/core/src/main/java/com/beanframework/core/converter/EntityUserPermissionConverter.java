package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.user.domain.UserPermission;

public class EntityUserPermissionConverter implements EntityConverter<UserPermissionDto, UserPermission> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermission convert(UserPermissionDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserPermission.UUID, source.getUuid());

				UserPermission prototype = modelService.findOneEntityByProperties(properties, true, UserPermission.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(UserPermission.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private UserPermission convert(UserPermissionDto source, UserPermission prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(source.getName(), prototype.getName()) == false) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getSort() == null) {
				if (prototype.getSort() != null) {
					prototype.setSort(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getSort() == null || prototype.getSort().equals(source.getSort()) == false) {
					prototype.setSort(source.getSort());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserPermissionFieldDto sourceField : source.getFields()) {

						if (prototype.getFields().get(i).getDynamicField().getUuid().equals(sourceField.getDynamicField().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == false) {
								prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
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