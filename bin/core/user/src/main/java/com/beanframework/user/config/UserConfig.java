package com.beanframework.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.user.converter.DtoAuditorConverter;
import com.beanframework.user.converter.DtoUserAuthorityConverter;
import com.beanframework.user.converter.DtoUserFieldConverter;
import com.beanframework.user.converter.DtoUserGroupConverter;
import com.beanframework.user.converter.DtoUserGroupFieldConverter;
import com.beanframework.user.converter.DtoUserPermissionConverter;
import com.beanframework.user.converter.DtoUserPermissionFieldConverter;
import com.beanframework.user.converter.DtoUserRightConverter;
import com.beanframework.user.converter.DtoUserRightFieldConverter;
import com.beanframework.user.converter.EntityUserGroupConverter;
import com.beanframework.user.converter.EntityUserPermissionConverter;
import com.beanframework.user.converter.EntityUserRightConverter;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;
import com.beanframework.user.interceptor.auditor.AuditorInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.auditor.AuditorLoadInterceptor;
import com.beanframework.user.interceptor.auditor.AuditorPrepareInterceptor;
import com.beanframework.user.interceptor.auditor.AuditorRemoveInterceptor;
import com.beanframework.user.interceptor.auditor.AuditorValidateInterceptor;
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
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class UserConfig {

	///////////////////
	// DTO Converter //
	///////////////////
	
	@Bean
	public DtoAuditorConverter dtoAuditorConverter() {
		return new DtoAuditorConverter();
	}

	@Bean
	public ConverterMapping dtoAuditorConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoAuditorConverter());
		mapping.setTypeCode(Auditor.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoUserAuthorityConverter dtoUserAuthorityConverter() {
		return new DtoUserAuthorityConverter();
	}

	@Bean
	public ConverterMapping dtoUserAuthorityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserAuthorityConverter());
		mapping.setTypeCode(UserAuthority.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoUserFieldConverter dtoUserFieldConverter() {
		return new DtoUserFieldConverter();
	}

	@Bean
	public ConverterMapping dtoUserFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserFieldConverter());
		mapping.setTypeCode(UserField.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoUserGroupConverter dtoUserGroupConverter() {
		return new DtoUserGroupConverter();
	}

	@Bean
	public ConverterMapping dtoUserGroupConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserGroupConverter());
		mapping.setTypeCode(UserGroup.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoUserGroupFieldConverter dtoUserGroupLangConverter() {
		return new DtoUserGroupFieldConverter();
	}

	@Bean
	public ConverterMapping dtoUserGroupLangConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserGroupLangConverter());
		mapping.setTypeCode(UserGroupField.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoUserPermissionConverter dtoUserPermissionConverter() {
		return new DtoUserPermissionConverter();
	}

	@Bean
	public ConverterMapping dtoUserPermissionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserPermissionConverter());
		mapping.setTypeCode(UserPermission.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoUserPermissionFieldConverter dtoUserPermissionLangConverter() {
		return new DtoUserPermissionFieldConverter();
	}

	@Bean
	public ConverterMapping dtoUserPermissionLangConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserPermissionLangConverter());
		mapping.setTypeCode(UserPermissionField.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoUserRightConverter dtoUserRightConverter() {
		return new DtoUserRightConverter();
	}

	@Bean
	public ConverterMapping DtoUserRightConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserRightConverter());
		mapping.setTypeCode(UserRight.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoUserRightFieldConverter dtoUserRightFieldConverter() {
		return new DtoUserRightFieldConverter();
	}

	@Bean
	public ConverterMapping DtoUserRightFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserRightFieldConverter());
		mapping.setTypeCode(UserRightField.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// ENTITY Converter //
	//////////////////////

	@Bean
	public EntityUserGroupConverter entityUserGroupConverter() {
		return new EntityUserGroupConverter();
	}

	@Bean
	public ConverterMapping entityUserGroupConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserGroupConverter());
		mapping.setTypeCode(UserGroup.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityUserPermissionConverter entityUserPermissionConverter() {
		return new EntityUserPermissionConverter();
	}

	@Bean
	public ConverterMapping entityUserPermissionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserPermissionConverter());
		mapping.setTypeCode(UserPermission.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityUserRightConverter entityUserRightConverter() {
		return new EntityUserRightConverter();
	}

	@Bean
	public ConverterMapping entityUserRightConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserRightConverter());
		mapping.setTypeCode(UserRight.class.getSimpleName());

		return mapping;
	}

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////
	
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
	public UserAuthorityLoadInterceptor userAuthorityLoadInterceptor() {
		return new UserAuthorityLoadInterceptor();
	}

	@Bean
	public InterceptorMapping userLoadInterceptorMapping() {
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
