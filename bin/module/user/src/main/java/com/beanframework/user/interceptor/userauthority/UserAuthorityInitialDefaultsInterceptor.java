package com.beanframework.user.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<UserAuthority> {

	@Override
	public void onInitialDefaults(UserAuthority model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
		model.setEnabled(Boolean.FALSE);
	}

}
