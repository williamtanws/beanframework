package com.beanframework.admin.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminFacade;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;

public class AdminValidateInterceptor implements ValidateInterceptor<Admin> {
	
	@Autowired
	private AdminFacade adminFacade;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(Admin model) throws InterceptorException {
		
		if (model.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(model.getId())) {
				throw new InterceptorException(localMessageService.getMessage(AdminConstants.Locale.ID_REQUIRED), this);
			} else if (StringUtils.isEmpty(model.getPassword())) {
				throw new InterceptorException(localMessageService.getMessage(AdminConstants.Locale.PASSWORD_REQUIRED),
						this);
			} else {
				Admin existsAdmin = adminFacade.findById(model.getId());
				if (existsAdmin != null) {
					throw new InterceptorException(localMessageService.getMessage(AdminConstants.Locale.ID_EXISTS),
							this);
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(model.getId())) {
				Admin existsAdmin = adminFacade.findById(model.getId());
				if (existsAdmin != null) {
					if (!model.getUuid().equals(existsAdmin.getUuid())) {
						throw new InterceptorException(localMessageService.getMessage(AdminConstants.Locale.ID_EXISTS),
								this);
					}
				}
			}
		}
	}

}
