package com.beanframework.core.interceptor.admin;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.Admin;

public class AdminLoadInterceptor extends AbstractLoadInterceptor<Admin> {

	@Override
	public void onLoad(Admin model, InterceptorContext context) throws InterceptorException {
	}

}
