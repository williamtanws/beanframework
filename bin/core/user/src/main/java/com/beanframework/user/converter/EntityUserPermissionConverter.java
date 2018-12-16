package com.beanframework.user.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionLang;


public class EntityUserPermissionConverter implements EntityConverter<UserPermission, UserPermission> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermission convert(UserPermission source) {

		UserPermission prototype = modelService.create(UserPermission.class);
		if (source.getUuid() != null) {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserPermission.UUID, source.getUuid());
			
			UserPermission exists = modelService.findOneEntityByProperties(properties, UserPermission.class);
			
			if (exists != null) {
				prototype = exists;
			}
		} else if (StringUtils.isNotEmpty(source.getId())) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserPermission.ID, source.getId());
			
			UserPermission exists = modelService.findOneEntityByProperties(properties, UserPermission.class);
			
			if (exists != null) {
				prototype = exists;
			}
		}

		return convert(source, prototype);
	}

	private UserPermission convert(UserPermission source, UserPermission prototype) {

		prototype.setId(source.getId());
		prototype.setSort(source.getSort());
		prototype.setLastModifiedDate(new Date());

		prototype.getUserPermissionLangs().clear();
		for (UserPermissionLang userPermissionLang : source.getUserPermissionLangs()) {
			if (userPermissionLang.getLanguage().getUuid() != null) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.UUID, userPermissionLang.getLanguage().getUuid());
				
				Language language = modelService.findOneEntityByProperties(properties, Language.class);
				
				if (language != null) {
					userPermissionLang.setLanguage(language);
					userPermissionLang.setUserPermission(prototype);
					prototype.getUserPermissionLangs().add(userPermissionLang);
				}
			} else if (StringUtils.isNotEmpty(userPermissionLang.getLanguage().getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.ID, userPermissionLang.getLanguage().getId());
				
				Language language = modelService.findOneEntityByProperties(properties, Language.class);
				
				if (language != null) {
					userPermissionLang.setLanguage(language);
					userPermissionLang.setUserPermission(prototype);
					prototype.getUserPermissionLangs().add(userPermissionLang);
				}
			}
		}

		return prototype;
	}

}
