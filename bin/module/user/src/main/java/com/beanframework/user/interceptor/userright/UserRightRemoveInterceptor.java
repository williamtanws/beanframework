package com.beanframework.user.interceptor.userright;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.user.domain.UserRight;

public class UserRightRemoveInterceptor implements RemoveInterceptor<UserRight> {

	@Override
	public void onRemove(UserRight model) throws InterceptorException {
	}

}
