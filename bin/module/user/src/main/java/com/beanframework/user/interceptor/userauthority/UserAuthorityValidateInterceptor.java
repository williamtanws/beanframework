package com.beanframework.user.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityValidateInterceptor implements ValidateInterceptor<UserAuthority> {

	@Override
	public void onValidate(UserAuthority model, InterceptorContext context) throws InterceptorException {

	}

}
