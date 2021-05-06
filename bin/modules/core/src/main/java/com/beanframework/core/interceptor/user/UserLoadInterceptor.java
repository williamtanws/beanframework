package com.beanframework.core.interceptor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

public class UserLoadInterceptor extends AbstractLoadInterceptor<User> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserLoadInterceptor.class);

	@Autowired
	private UserService userService;

	@Value(UserConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
	private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

	@Override
	public void onLoad(User model, InterceptorContext context) throws InterceptorException {
		try {
			userService.generateUserField(model, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
