package com.beanframework.user.interceptor.user;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.User;

public class UserListLoadInterceptor extends AbstractLoadInterceptor<User> {

	@Override
	public void onLoad(User model, InterceptorContext context) throws InterceptorException {
		User prototype = new User();
		loadCommonProperties(model, prototype, context);
		prototype.setType(model.getType());
		prototype.setPassword(model.getPassword());
		prototype.setAccountNonExpired(model.getAccountNonExpired());
		prototype.setAccountNonLocked(model.getAccountNonLocked());
		prototype.setCredentialsNonExpired(model.getCredentialsNonExpired());
		prototype.setEnabled(model.getEnabled());
		prototype.setName(model.getName());

		model = prototype;
	}
}
