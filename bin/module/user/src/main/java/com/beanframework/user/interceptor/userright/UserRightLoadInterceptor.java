package com.beanframework.user.interceptor.userright;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserRight;

public class UserRightLoadInterceptor extends AbstractLoadInterceptor<UserRight> {

	@Override
	public UserRight onLoad(UserRight model, InterceptorContext context) throws InterceptorException {
		UserRight prototype = new UserRight();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setSort(model.getSort());

		return prototype;
	}
}
