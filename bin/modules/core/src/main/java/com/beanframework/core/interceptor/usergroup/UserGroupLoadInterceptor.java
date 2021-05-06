package com.beanframework.core.interceptor.usergroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;

public class UserGroupLoadInterceptor extends AbstractLoadInterceptor<UserGroup> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserGroupLoadInterceptor.class);
	
	@Autowired
	private UserGroupService userGroupService;

	@Override
	public void onLoad(UserGroup model, InterceptorContext context) throws InterceptorException {
		
		try {
			userGroupService.generateUserGroupField(model);
			userGroupService.generateUserAuthority(model);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
