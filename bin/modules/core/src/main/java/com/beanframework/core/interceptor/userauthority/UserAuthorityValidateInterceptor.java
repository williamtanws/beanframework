package com.beanframework.core.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityValidateInterceptor extends AbstractValidateInterceptor<UserAuthority> {

	@Override
	public void onValidate(UserAuthority model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
