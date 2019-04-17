package com.beanframework.user.interceptor.usergroup;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupListLoadInterceptor extends AbstractLoadInterceptor<UserGroup> {

	@Override
	public UserGroup onLoad(UserGroup model, InterceptorContext context) throws InterceptorException {
		UserGroup prototype = new UserGroup();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());

		return prototype;
	}
}
