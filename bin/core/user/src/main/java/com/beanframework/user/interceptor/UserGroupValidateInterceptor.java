package com.beanframework.user.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.UserGroupConstants;
import com.beanframework.user.domain.UserGroup;

public class UserGroupValidateInterceptor implements ValidateInterceptor<UserGroup> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(UserGroup model) throws InterceptorException {

		try {
			if (model.getUuid() == null) {
				// Save new
				if (StringUtils.isEmpty(model.getId())) {
					throw new InterceptorException(
							localMessageService.getMessage(UserGroupConstants.Locale.ID_REQUIRED), this);
				} else {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(UserGroup.ID, model.getId());
					boolean exists = modelService.existsByProperties(properties, UserGroup.class);
					if (exists) {
						throw new InterceptorException(
								localMessageService.getMessage(UserGroupConstants.Locale.ID_EXISTS), this);
					}
				}

			} else {
				// Update exists
				if (StringUtils.isNotEmpty(model.getId())) {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(UserGroup.ID, model.getId());
					UserGroup exists = modelService.findOneEntityByProperties(properties, UserGroup.class);
					if (exists != null) {
						if (!model.getUuid().equals(exists.getUuid())) {
							throw new InterceptorException(
									localMessageService.getMessage(UserGroupConstants.Locale.ID_EXISTS), this);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
