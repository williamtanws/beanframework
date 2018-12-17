package com.beanframework.user.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;


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
		}

		return convert(source, prototype);
	}

	private UserRight convert(UserRight source, UserRight prototype) {

		prototype.setLastModifiedDate(new Date());
		prototype.setId(source.getId());
		
		if(source.getSort() != null) {
			prototype.setSort(source.getSort());
		}
		if(source.getUserRightFields() != null) {
			prototype.setUserRightFields(source.getUserRightFields());
		}
		
//		prototype.getUserRightFields().clear();
//		for (UserRightField userRightField : source.getUserRightFields()) {
//			if (userRightField.getLanguage().getUuid() != null) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Language.UUID, userRightField.getLanguage().getUuid());
//				
//				Language language = modelService.findOneEntityByProperties(properties, Language.class);
//				
//				if (language != null) {
//					userRightField.setLanguage(language);
//					userRightField.setUserRight(prototype);
//					prototype.getUserRightFields().add(userRightField);
//				}
//			} else if (StringUtils.isNotEmpty(userRightField.getLanguage().getId())) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Language.ID, userRightField.getLanguage().getId());
//				
//				Language language = modelService.findOneEntityByProperties(properties, Language.class);
//				
//				if (language != null) {
//					userRightField.setLanguage(language);
//					userRightField.setUserRight(prototype);
//					prototype.getUserRightFields().add(userRightField);
//				}
//			}
//		}

		return prototype;
	}

}
