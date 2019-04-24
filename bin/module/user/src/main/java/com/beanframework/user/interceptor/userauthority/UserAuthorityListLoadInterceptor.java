package com.beanframework.user.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityListLoadInterceptor extends AbstractLoadInterceptor<UserAuthority> {

	@Override
	public UserAuthority onLoad(UserAuthority model, InterceptorContext context) throws InterceptorException {
		UserAuthority prototype = new UserAuthority();
		loadCommonProperties(model, prototype, context);
		prototype.setEnabled(model.getEnabled());

		return prototype;
	}

}
