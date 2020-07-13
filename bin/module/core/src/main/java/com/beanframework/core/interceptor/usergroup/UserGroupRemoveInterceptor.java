package com.beanframework.core.interceptor.usergroup;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupRemoveInterceptor extends AbstractRemoveInterceptor<UserGroup> {

	@Override
	public void onRemove(UserGroup model, InterceptorContext context) throws InterceptorException {
	}

}
