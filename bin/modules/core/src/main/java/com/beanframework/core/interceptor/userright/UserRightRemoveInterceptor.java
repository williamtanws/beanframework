package com.beanframework.core.interceptor.userright;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.user.domain.UserRight;

public class UserRightRemoveInterceptor extends AbstractRemoveInterceptor<UserRight> {

	@Override
	public void onRemove(UserRight model, InterceptorContext context) throws InterceptorException {
	}

}
