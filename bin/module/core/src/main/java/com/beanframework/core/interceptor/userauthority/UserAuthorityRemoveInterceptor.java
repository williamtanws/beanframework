package com.beanframework.core.interceptor.userauthority;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityRemoveInterceptor extends AbstractRemoveInterceptor<UserAuthority> {

	@Override
	public void onRemove(UserAuthority model, InterceptorContext context) throws InterceptorException {
	}

}
