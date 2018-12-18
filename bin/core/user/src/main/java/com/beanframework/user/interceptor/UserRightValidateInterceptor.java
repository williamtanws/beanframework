package com.beanframework.user.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.UserRightConstants;
import com.beanframework.user.domain.UserRight;

public class UserRightValidateInterceptor implements ValidateInterceptor<UserRight> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(UserRight model) throws InterceptorException {

		try {
			if (model.getUuid() == null) {
				// Save new
				if (StringUtils.isEmpty(model.getId())) {
					throw new InterceptorException(
							localMessageService.getMessage(UserRightConstants.Locale.ID_REQUIRED), this);
				} else {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(UserRight.ID, model.getId());
					boolean exists = modelService.existsByProperties(properties, UserRight.class);
					if (exists) {
						throw new InterceptorException(
								localMessageService.getMessage(UserRightConstants.Locale.ID_EXISTS), this);
					}
				}

			} else {
				// Update exists
				if (StringUtils.isNotEmpty(model.getId())) {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(UserRight.ID, model.getId());
					UserRight exists = modelService.findOneEntityByProperties(properties, UserRight.class);
					if (exists != null) {
						if (!model.getUuid().equals(exists.getUuid())) {
							throw new InterceptorException(
									localMessageService.getMessage(UserRightConstants.Locale.ID_EXISTS), this);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
