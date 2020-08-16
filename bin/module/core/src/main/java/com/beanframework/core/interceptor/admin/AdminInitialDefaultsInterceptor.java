package com.beanframework.core.interceptor.admin;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.user.domain.Admin;

public class AdminInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Admin> {

	@Override
	public void onInitialDefaults(Admin model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
		model.setEnabled(true);
		model.setAccountNonExpired(true);
		model.setAccountNonLocked(true);
		model.setCredentialsNonExpired(true);
	}

}
