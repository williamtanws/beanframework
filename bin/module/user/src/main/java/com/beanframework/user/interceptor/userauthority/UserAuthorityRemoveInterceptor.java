package com.beanframework.user.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityRemoveInterceptor implements RemoveInterceptor<UserAuthority> {

	@Override
	public void onRemove(UserAuthority model, InterceptorContext context) throws InterceptorException {
	}

}
