package com.beanframework.admin.interceptor;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;

public class AdminRemoveInterceptor implements RemoveInterceptor<Admin> {

	@Override
	public void onRemove(Admin model, InterceptorContext context) throws InterceptorException {
	}

}
