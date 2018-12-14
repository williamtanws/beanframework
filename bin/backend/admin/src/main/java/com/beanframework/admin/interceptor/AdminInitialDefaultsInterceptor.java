package com.beanframework.admin.interceptor;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;

public class AdminInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Admin> {

	@Override
	public void onInitialDefaults(Admin model) throws InterceptorException {
		model.setEnabled(true);
		model.setAccountNonExpired(true);
		model.setAccountNonLocked(true);
		model.setCredentialsNonExpired(true);
	}

}
