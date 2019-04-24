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
import com.beanframework.user.interceptor.auditor.AuditorInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.auditor.AuditorLoadInterceptor;
import com.beanframework.user.interceptor.auditor.AuditorPrepareInterceptor;
import com.beanframework.user.interceptor.auditor.AuditorRemoveInterceptor;
import com.beanframework.user.interceptor.auditor.AuditorValidateInterceptor;
import com.beanframework.user.interceptor.user.UserInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.user.UserLoadInterceptor;
import com.beanframework.user.interceptor.user.UserPrepareInterceptor;
import com.beanframework.user.interceptor.user.UserRemoveInterceptor;
import com.beanframework.user.interceptor.user.UserValidateInterceptor;
import com.beanframework.user.interceptor.userauthority.UserAuthorityInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.userauthority.UserAuthorityLoadInterceptor;
import com.beanframework.user.interceptor.userauthority.UserAuthorityPrepareInterceptor;
import com.beanframework.user.interceptor.userauthority.UserAuthorityRemoveInterceptor;
import com.beanframework.user.interceptor.userauthority.UserAuthorityValidateInterceptor;
import com.beanframework.user.interceptor.usergroup.UserGroupInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.usergroup.UserGroupLoadInterceptor;
import com.beanframework.user.interceptor.usergroup.UserGroupPrepareInterceptor;
import com.beanframework.user.interceptor.usergroup.UserGroupRemoveInterceptor;
import com.beanframework.user.interceptor.usergroup.UserGroupValidateInterceptor;
import com.beanframework.user.interceptor.userpermission.UserPermissionInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.userpermission.UserPermissionLoadInterceptor;
import com.beanframework.user.interceptor.userpermission.UserPermissionPrepareInterceptor;
import com.beanframework.user.interceptor.userpermission.UserPermissionRemoveInterceptor;
import com.beanframework.user.interceptor.userpermission.UserPermissionValidateInterceptor;
import com.beanframework.user.interceptor.userright.UserRightInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.userright.UserRightLoadInterceptor;
import com.beanframework.user.interceptor.userright.UserRightPrepareInterceptor;
import com.beanframework.user.interceptor.userright.UserRightRemoveInterceptor;
import com.beanframework.user.interceptor.userright.UserRightValidateInterceptor;

@Configuration
public class UserInterceptorConfig {

	/////////////////////////////////
	// Initial Defaults Interceptor //
	/////////////////////////////////

	@Bean
	public AuditorInitialDefaultsInterceptor auditorInitialDefaultsInterceptor() {
		return new AuditorInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping auditorInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(auditorInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(Auditor.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserInitialDefaultsInterceptor userInitialDefaultsInterceptor() {
		return new UserInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping userInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(User.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserAuthorityInitialDefaultsInterceptor userAuthorityInitialDefaultsInterceptor() {
		return new UserAuthorityInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping userAuthorityInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userAuthorityInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(UserAuthority.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserGroupInitialDefaultsInterceptor userGroupInitialDefaultsInterceptor() {
		return new UserGroupInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping userGroupInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userGroupInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserPermissionInitialDefaultsInterceptor userPermissionInitialDefaultsInterceptor() {
		return new UserPermissionInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping userPermissionInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userPermissionInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(UserPermission.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserRightInitialDefaultsInterceptor userRightInitialDefaultsInterceptor() {
		return new UserRightInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping userRightInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userRightInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(UserRight.class.getSimpleName());

		return interceptorMapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public AuditorLoadInterceptor auditorLoadInterceptor() {
		return new AuditorLoadInterceptor();
	}

	@Bean
	public InterceptorMapping auditorLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(auditorLoadInterceptor());
		interceptorMapping.setTypeCode(Auditor.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserLoadInterceptor userLoadInterceptor() {
		return new UserLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userLoadInterceptor());
		interceptorMapping.setTypeCode(User.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserAuthorityLoadInterceptor userAuthorityLoadInterceptor() {
		return new UserAuthorityLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userAuthorityLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userAuthorityLoadInterceptor());
		interceptorMapping.setTypeCode(UserAuthority.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserGroupLoadInterceptor userGroupLoadInterceptor() {
		return new UserGroupLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userGroupLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userGroupLoadInterceptor());
		interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserPermissionLoadInterceptor userPermissionLoadInterceptor() {
		return new UserPermissionLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userPermissionLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userPermissionLoadInterceptor());
		interceptorMapping.setTypeCode(UserPermission.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserRightLoadInterceptor userRightLoadInterceptor() {
		return new UserRightLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userRightLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userRightLoadInterceptor());
		interceptorMapping.setTypeCode(UserRight.class.getSimpleName());

		return interceptorMapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public AuditorPrepareInterceptor auditorPrepareInterceptor() {
		return new AuditorPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping auditorPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(auditorPrepareInterceptor());
		interceptorMapping.setTypeCode(Auditor.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserPrepareInterceptor userPrepareInterceptor() {
		return new UserPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping userPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userPrepareInterceptor());
		interceptorMapping.setTypeCode(User.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserAuthorityPrepareInterceptor userAuthorityPrepareInterceptor() {
		return new UserAuthorityPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping userAuthorityPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userAuthorityPrepareInterceptor());
		interceptorMapping.setTypeCode(UserAuthority.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserGroupPrepareInterceptor userGroupPrepareInterceptor() {
		return new UserGroupPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping userGroupPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userGroupPrepareInterceptor());
		interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserPermissionPrepareInterceptor userPermissionPrepareInterceptor() {
		return new UserPermissionPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping userPermissionPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userPermissionPrepareInterceptor());
		interceptorMapping.setTypeCode(UserPermission.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserRightPrepareInterceptor userRightPrepareInterceptor() {
		return new UserRightPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping userRightPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userRightPrepareInterceptor());
		interceptorMapping.setTypeCode(UserRight.class.getSimpleName());

		return interceptorMapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public AuditorValidateInterceptor auditorValidateInterceptor() {
		return new AuditorValidateInterceptor();
	}

	@Bean
	public InterceptorMapping auditorValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(auditorValidateInterceptor());
		interceptorMapping.setTypeCode(Auditor.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserValidateInterceptor userValidateInterceptor() {
		return new UserValidateInterceptor();
	}

	@Bean
	public InterceptorMapping userValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userValidateInterceptor());
		interceptorMapping.setTypeCode(User.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserAuthorityValidateInterceptor userAuthorityValidateInterceptor() {
		return new UserAuthorityValidateInterceptor();
	}

	@Bean
	public InterceptorMapping userAuthorityValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userAuthorityValidateInterceptor());
		interceptorMapping.setTypeCode(UserAuthority.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserGroupValidateInterceptor userGroupValidateInterceptor() {
		return new UserGroupValidateInterceptor();
	}

	@Bean
	public InterceptorMapping userGroupValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userGroupValidateInterceptor());
		interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserPermissionValidateInterceptor userPermissionValidateInterceptor() {
		return new UserPermissionValidateInterceptor();
	}

	@Bean
	public InterceptorMapping userPermissionValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userPermissionValidateInterceptor());
		interceptorMapping.setTypeCode(UserPermission.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserRightValidateInterceptor userRightValidateInterceptor() {
		return new UserRightValidateInterceptor();
	}

	@Bean
	public InterceptorMapping userRightValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userRightValidateInterceptor());
		interceptorMapping.setTypeCode(UserRight.class.getSimpleName());

		return interceptorMapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public AuditorRemoveInterceptor auditorRemoveInterceptor() {
		return new AuditorRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping auditorRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(auditorRemoveInterceptor());
		interceptorMapping.setTypeCode(Auditor.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserRemoveInterceptor userRemoveInterceptor() {
		return new UserRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping userRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userRemoveInterceptor());
		interceptorMapping.setTypeCode(User.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserAuthorityRemoveInterceptor userAuthorityRemoveInterceptor() {
		return new UserAuthorityRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping userAuthorityRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userAuthorityRemoveInterceptor());
		interceptorMapping.setTypeCode(UserAuthority.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserGroupRemoveInterceptor userGroupRemoveInterceptor() {
		return new UserGroupRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping userGroupRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userGroupRemoveInterceptor());
		interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserPermissionRemoveInterceptor userPermissionRemoveInterceptor() {
		return new UserPermissionRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping userPermissionRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userPermissionRemoveInterceptor());
		interceptorMapping.setTypeCode(UserPermission.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public UserRightRemoveInterceptor userRightRemoveInterceptor() {
		return new UserRightRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping userRightRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userRightRemoveInterceptor());
		interceptorMapping.setTypeCode(UserRight.class.getSimpleName());

		return interceptorMapping;
	}
}
