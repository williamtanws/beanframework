package com.beanframework.user.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityInitialDefaultsInterceptor implements InitialDefaultsInterceptor<UserAuthority> {

	@Override
	public void onInitialDefaults(UserAuthority model) throws InterceptorException {
		model.setEnabled(Boolean.FALSE);
	}

}
