package com.beanframework.user.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;

public class UserValidateInterceptor implements ValidateInterceptor<User> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(User model) throws InterceptorException {

		try {
			if (model.getUuid() == null) {
				// Save new
				if (StringUtils.isBlank(model.getId())) {
					throw new InterceptorException(localMessageService.getMessage(UserConstants.Locale.ID_REQUIRED), this);
				} else if (StringUtils.isBlank(model.getPassword())) {
					throw new InterceptorException(localMessageService.getMessage(UserConstants.Locale.PASSWORD_REQUIRED),
							this);
				} else {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(User.ID, model.getId());
					boolean exists = modelService.existsByProperties(properties, User.class);
					if (exists) {
						throw new InterceptorException(localMessageService.getMessage(UserConstants.Locale.ID_EXISTS),
								this);
					}
				}

			} else {
				// Update exists
				if (StringUtils.isNotBlank(model.getId())) {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(User.ID, model.getId());
					User exists = modelService.findOneEntityByProperties(properties, User.class);
					if (exists != null) {
						if (!model.getUuid().equals(exists.getUuid())) {
							throw new InterceptorException(localMessageService.getMessage(UserConstants.Locale.ID_EXISTS),
									this);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
