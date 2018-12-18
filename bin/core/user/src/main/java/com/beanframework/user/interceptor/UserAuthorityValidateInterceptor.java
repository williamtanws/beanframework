package com.beanframework.user.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.UserAuthorityConstants;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityValidateInterceptor implements ValidateInterceptor<UserAuthority> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(UserAuthority model) throws InterceptorException {

		try {
			if (model.getUuid() == null) {
				// Save new
				if (StringUtils.isEmpty(model.getId())) {
					throw new InterceptorException(
							localMessageService.getMessage(UserAuthorityConstants.Locale.ID_REQUIRED), this);
				} else {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(UserAuthority.ID, model.getId());
					boolean exists = modelService.existsByProperties(properties, UserAuthority.class);
					if (exists) {
						throw new InterceptorException(
								localMessageService.getMessage(UserAuthorityConstants.Locale.ID_EXISTS), this);
					}
				}

			} else {
				// Update exists
				if (StringUtils.isNotEmpty(model.getId())) {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(UserAuthority.ID, model.getId());
					UserAuthority exists = modelService.findOneEntityByProperties(properties, UserAuthority.class);
					if (exists != null) {
						if (!model.getUuid().equals(exists.getUuid())) {
							throw new InterceptorException(
									localMessageService.getMessage(UserAuthorityConstants.Locale.ID_EXISTS), this);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
