package com.beanframework.user.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
public class SaveUserGroupValidator implements Validator {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserGroup.class == clazz;
	}

	@Override
	public void validate(Object target, Errors errors) {
		final UserGroup group = (UserGroup) target;

		if (group.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(group.getId())) {
				errors.reject(UserGroup.ID,
						localMessageService.getMessage(UserGroupConstants.Locale.ID_REQUIRED));
			} else {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRight.ID, group.getId());
				
				boolean existsGroup = modelService.existsByProperties(properties, UserGroup.class);
				if (existsGroup) {
					errors.reject(UserGroup.ID,
							localMessageService.getMessage(UserGroupConstants.Locale.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(group.getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRight.ID, group.getId());
				
				UserGroup existsGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
				
				if (existsGroup != null) {
					if (!group.getUuid().equals(existsGroup.getUuid())) {
						errors.reject(UserGroup.ID,
								localMessageService.getMessage(UserGroupConstants.Locale.ID_EXISTS));
					}
				}
			}
		}
	}

}
