package com.beanframework.core.interceptor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.User;

public class UserLoadInterceptor extends AbstractLoadInterceptor<User> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserLoadInterceptor.class);

	@Override
	public void onLoad(User model, InterceptorContext context) throws InterceptorException {

	}

}
