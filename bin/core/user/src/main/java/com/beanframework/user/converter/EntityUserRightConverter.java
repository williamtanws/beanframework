package com.beanframework.user.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightLang;


public class EntityUserRightConverter implements EntityConverter<UserRight, UserRight> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public UserRight convert(UserRight source) {

		UserRight prototype = modelService.create(UserRight.class);
		if (source.getUuid() != null) {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserRight.UUID, source.getUuid());
			
			UserRight exists = modelService.findOneEntityByProperties(properties, UserRight.class);
			
			if (exists != null) {
				prototype = exists;
			}
		} else if (StringUtils.isNotEmpty(source.getId())) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserRight.ID, source.getId());
			
			UserRight exists = modelService.findOneEntityByProperties(properties, UserRight.class);
			
			if (exists != null) {
				prototype = exists;
			}
		}

		return convert(source, prototype);
	}

	private UserRight convert(UserRight source, UserRight prototype) {

		prototype.setId(source.getId());
		prototype.setSort(source.getSort());
		prototype.setLastModifiedDate(new Date());

		prototype.getUserRightLangs().clear();
		for (UserRightLang userRightLang : source.getUserRightLangs()) {
			if (userRightLang.getLanguage().getUuid() != null) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.UUID, userRightLang.getLanguage().getUuid());
				
				Language language = modelService.findOneEntityByProperties(properties, Language.class);
				
				if (language != null) {
					userRightLang.setLanguage(language);
					userRightLang.setUserRight(prototype);
					prototype.getUserRightLangs().add(userRightLang);
				}
			} else if (StringUtils.isNotEmpty(userRightLang.getLanguage().getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.ID, userRightLang.getLanguage().getId());
				
				Language language = modelService.findOneEntityByProperties(properties, Language.class);
				
				if (language != null) {
					userRightLang.setLanguage(language);
					userRightLang.setUserRight(prototype);
					prototype.getUserRightLangs().add(userRightLang);
				}
			}
		}

		return prototype;
	}

}
