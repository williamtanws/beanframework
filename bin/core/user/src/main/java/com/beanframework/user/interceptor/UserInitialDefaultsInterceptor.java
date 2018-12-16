package com.beanframework.user.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.user.domain.User;

public class UserInitialDefaultsInterceptor implements InitialDefaultsInterceptor<User> {

	@Override
	public void onInitialDefaults(User model) throws InterceptorException {
		model.setEnabled(true);
		model.setAccountNonExpired(true);
		model.setAccountNonLocked(true);
		model.setCredentialsNonExpired(true);
	}

}
