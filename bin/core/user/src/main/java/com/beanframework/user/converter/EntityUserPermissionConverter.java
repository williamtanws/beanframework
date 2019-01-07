package com.beanframework.user.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

public class EntityUserPermissionConverter implements EntityConverter<UserPermission, UserPermission> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermission convert(UserPermission source) throws ConverterException {

		UserPermission prototype;
		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserPermission.UUID, source.getUuid());

				UserPermission exists = modelService.findOneEntityByProperties(properties, UserPermission.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(UserPermission.class);
				}
			} else {
				prototype = modelService.create(UserPermission.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private UserPermission convert(UserPermission source, UserPermission prototype) throws ConverterException {

		try {
			

			if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
				prototype.setId(source.getId());

			if (source.getSort() != prototype.getSort())
				prototype.setSort(source.getSort());
			
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserPermissionField sourceUserPermissionField : source.getFields()) {
						if (prototype.getFields().get(i).getUuid().equals(sourceUserPermissionField.getUuid())) {
							prototype.getFields().get(i).setValue(sourceUserPermissionField.getValue());
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
