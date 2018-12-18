package com.beanframework.admin.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.domain.Admin;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;

public class AdminValidateInterceptor implements ValidateInterceptor<Admin> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(Admin model) throws InterceptorException {
		try {
			if (model.getUuid() == null) {
				// Save new
				if (StringUtils.isEmpty(model.getId())) {
					throw new InterceptorException(localMessageService.getMessage(AdminConstants.Locale.ID_REQUIRED),
							this);
				} else if (StringUtils.isEmpty(model.getPassword())) {
					throw new InterceptorException(
							localMessageService.getMessage(AdminConstants.Locale.PASSWORD_REQUIRED), this);
				} else {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(Admin.ID, model.getId());
					boolean exists = modelService.existsByProperties(properties, Admin.class);
					if (exists) {
						throw new InterceptorException(localMessageService.getMessage(AdminConstants.Locale.ID_EXISTS),
								this);
					}
				}

			} else {
				// Update exists
				if (StringUtils.isNotEmpty(model.getId())) {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(Admin.ID, model.getId());
					Admin exists = modelService.findOneEntityByProperties(properties, Admin.class);

					if (exists != null) {
						if (!model.getUuid().equals(exists.getUuid())) {
							throw new InterceptorException(
									localMessageService.getMessage(AdminConstants.Locale.ID_EXISTS), this);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), this);
		}
	}

}
