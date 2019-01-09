package com.beanframework.user.interceptor.userright;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.user.domain.UserRight;

public class UserRightValidateInterceptor implements ValidateInterceptor<UserRight> {

	@Override
	public void onValidate(UserRight model) throws InterceptorException {
	}

}
