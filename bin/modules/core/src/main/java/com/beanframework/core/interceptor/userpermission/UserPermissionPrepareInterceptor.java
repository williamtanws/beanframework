package com.beanframework.core.interceptor.userpermission;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionPrepareInterceptor extends AbstractPrepareInterceptor<UserPermission> {

	@Override
	public void onPrepare(UserPermission model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}
}
