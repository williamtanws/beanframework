package com.beanframework.admin.interceptor;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;

public class AdminValidateInterceptor implements ValidateInterceptor<Admin> {

	@Override
	public void onValidate(Admin model) throws InterceptorException {
	
	}

}
