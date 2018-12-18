package com.beanframework.user.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserPermission;

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

	private UserPermission convert(UserPermission source, UserPermission prototype) {

		prototype.setLastModifiedDate(new Date());
		prototype.setId(source.getId());

		if (source.getSort() != null) {
			prototype.setSort(source.getSort());
		}
		if (source.getUserPermissionFields() != null) {
			prototype.setUserPermissionFields(source.getUserPermissionFields());
		}
//		prototype.getUserPermissionFields().clear();
//		for (UserPermissionField userPermissionLang : source.getUserPermissionFields()) {
//			if (userPermissionLang.getLanguage().getUuid() != null) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Language.UUID, userPermissionLang.getLanguage().getUuid());
//				
//				Language language = modelService.findOneEntityByProperties(properties, Language.class);
//				
//				if (language != null) {
//					userPermissionLang.setLanguage(language);
//					userPermissionLang.setUserPermission(prototype);
//					prototype.getUserPermissionFields().add(userPermissionLang);
//				}
//			} else if (StringUtils.isNotEmpty(userPermissionLang.getLanguage().getId())) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Language.ID, userPermissionLang.getLanguage().getId());
//				
//				Language language = modelService.findOneEntityByProperties(properties, Language.class);
//				
//				if (language != null) {
//					userPermissionLang.setLanguage(language);
//					userPermissionLang.setUserPermission(prototype);
//					prototype.getUserPermissionFields().add(userPermissionLang);
//				}
//			}
//		}

		return prototype;
	}

}
