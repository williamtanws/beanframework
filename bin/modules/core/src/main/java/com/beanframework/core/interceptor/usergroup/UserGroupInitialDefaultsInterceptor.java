package com.beanframework.core.interceptor.usergroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;

public class UserGroupInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<UserGroup> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserGroupInitialDefaultsInterceptor.class);

	@Autowired
	private UserGroupService userGroupService;

	@Override
	public void onInitialDefaults(UserGroup model, InterceptorContext context) throws InterceptorException {
		try {
			userGroupService.generateUserGroupField(model);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
