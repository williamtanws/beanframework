package com.beanframework.user.interceptor.usergroup;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupRemoveInterceptor implements RemoveInterceptor<UserGroup> {

	@Override
	public void onRemove(UserGroup model, InterceptorContext context) throws InterceptorException {
	}

}
