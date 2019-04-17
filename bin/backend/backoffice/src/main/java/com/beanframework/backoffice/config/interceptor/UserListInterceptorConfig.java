package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.domain.Auditor;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.interceptor.auditor.AuditorListLoadInterceptor;
import com.beanframework.user.interceptor.user.UserListLoadInterceptor;
import com.beanframework.user.interceptor.userauthority.UserAuthorityListLoadInterceptor;
import com.beanframework.user.interceptor.usergroup.UserGroupListLoadInterceptor;
import com.beanframework.user.interceptor.userpermission.UserPermissionListLoadInterceptor;
import com.beanframework.user.interceptor.userright.UserRightListLoadInterceptor;

@Configuration
public class UserListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public AuditorListLoadInterceptor auditorListLoadInterceptor() {
		return new AuditorListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping auditorListLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(auditorListLoadInterceptor());
		interceptorMapping.setTypeCode(Auditor.class.getSimpleName() + "List");

		return interceptorMapping;
	}

	@Bean
	public UserListLoadInterceptor userListLoadInterceptor() {
		return new UserListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userListLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userListLoadInterceptor());
		interceptorMapping.setTypeCode(User.class.getSimpleName() + "List");

		return interceptorMapping;
	}

	@Bean
	public UserAuthorityListLoadInterceptor userAuthorityListLoadInterceptor() {
		return new UserAuthorityListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userAuthorityListLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userAuthorityListLoadInterceptor());
		interceptorMapping.setTypeCode(UserAuthority.class.getSimpleName() + "List");

		return interceptorMapping;
	}

	@Bean
	public UserGroupListLoadInterceptor userGroupListLoadInterceptor() {
		return new UserGroupListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userGroupListLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userGroupListLoadInterceptor());
		interceptorMapping.setTypeCode(UserGroup.class.getSimpleName() + "List");

		return interceptorMapping;
	}

	@Bean
	public UserPermissionListLoadInterceptor userPermissionListLoadInterceptor() {
		return new UserPermissionListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userPermissionListLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userPermissionListLoadInterceptor());
		interceptorMapping.setTypeCode(UserPermission.class.getSimpleName() + "List");

		return interceptorMapping;
	}

	@Bean
	public UserRightListLoadInterceptor userRightListLoadInterceptor() {
		return new UserRightListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userRightListLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userRightListLoadInterceptor());
		interceptorMapping.setTypeCode(UserRight.class.getSimpleName() + "List");

		return interceptorMapping;
	}
}
