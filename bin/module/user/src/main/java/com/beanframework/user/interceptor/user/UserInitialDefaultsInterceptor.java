package com.beanframework.user.interceptor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.user.domain.User;

public class UserInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<User> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserInitialDefaultsInterceptor.class);

	@Override
	public void onInitialDefaults(User model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
		model.setEnabled(true);
		model.setAccountNonExpired(true);
		model.setAccountNonLocked(true);
		model.setCredentialsNonExpired(true);
	}

}
