package com.beanframework.user.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.UserGroupConstants;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserRight;

@Component
public class DeleteUserGroupValidator implements Validator {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UUID.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(UserRight.UUID, uuid);
		
		UserGroup userGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
		
		if(userGroup == null) {
			errors.reject(UserGroup.ID, localMessageService.getMessage(UserGroupConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
