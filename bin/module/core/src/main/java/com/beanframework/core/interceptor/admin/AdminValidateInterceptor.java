package com.beanframework.core.interceptor.admin;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.Admin;

public class AdminValidateInterceptor extends AbstractValidateInterceptor<Admin> {

	@Override
	public void onValidate(Admin model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
