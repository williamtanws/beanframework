package com.beanframework.core.interceptor.admin;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.user.domain.Admin;

public class AdminRemoveInterceptor extends AbstractRemoveInterceptor<Admin> {

	@Override
	public void onRemove(Admin model, InterceptorContext context) throws InterceptorException {
	}

}
