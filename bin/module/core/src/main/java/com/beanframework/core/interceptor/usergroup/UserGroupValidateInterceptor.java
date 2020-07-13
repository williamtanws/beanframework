package com.beanframework.core.interceptor.usergroup;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupValidateInterceptor extends AbstractValidateInterceptor<UserGroup> {

	@Override
	public void onValidate(UserGroup model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
