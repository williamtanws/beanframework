package com.beanframework.core.interceptor.admin;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;

public class AdminValidateInterceptor extends AbstractValidateInterceptor<Admin> {

	@Override
	public void onValidate(Admin model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}