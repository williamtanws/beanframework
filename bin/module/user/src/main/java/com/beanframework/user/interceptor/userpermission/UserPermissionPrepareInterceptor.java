package com.beanframework.user.interceptor.userpermission;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionPrepareInterceptor implements PrepareInterceptor<UserPermission> {

	@Override
	public void onPrepare(UserPermission model) throws InterceptorException {		
		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}
}
