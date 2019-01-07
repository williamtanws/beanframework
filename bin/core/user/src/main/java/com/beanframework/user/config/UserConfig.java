package com.beanframework.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.user.converter.DtoUserAuthorityConverter;
import com.beanframework.user.converter.DtoUserFieldConverter;
import com.beanframework.user.converter.DtoUserGroupConverter;
import com.beanframework.user.converter.DtoUserGroupFieldConverter;
import com.beanframework.user.converter.DtoUserPermissionConverter;
import com.beanframework.user.converter.DtoUserPermissionFieldConverter;
import com.beanframework.user.converter.DtoUserRightConverter;
import com.beanframework.user.converter.DtoUserRightFieldConverter;
import com.beanframework.user.converter.EntityUserConverter;
import com.beanframework.user.converter.EntityUserFieldConverter;
import com.beanframework.user.converter.EntityUserGroupConverter;
import com.beanframework.user.converter.EntityUserGroupFieldConverter;
import com.beanframework.user.converter.EntityUserPermissionConverter;
import com.beanframework.user.converter.EntityUserPermissionFieldConverter;
import com.beanframework.user.converter.EntityUserRightConverter;
import com.beanframework.user.converter.EntityUserRightFieldConverter;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;
import com.beanframework.user.interceptor.UserAuthorityInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.UserAuthorityValidateInterceptor;
import com.beanframework.user.interceptor.UserGroupLoadInterceptor;
import com.beanframework.user.interceptor.UserGroupPrepareInterceptor;
import com.beanframework.user.interceptor.UserGroupValidateInterceptor;
import com.beanframework.user.interceptor.UserInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.UserLoadInterceptor;
import com.beanframework.user.interceptor.UserPermissionPrepareInterceptor;
import com.beanframework.user.interceptor.UserPermissionValidateInterceptor;
import com.beanframework.user.interceptor.UserPrepareInterceptor;
import com.beanframework.user.interceptor.UserRemoveInterceptor;
import com.beanframework.user.interceptor.UserRightPrepareInterceptor;
import com.beanframework.user.interceptor.UserRightValidateInterceptor;
import com.beanframework.user.interceptor.UserValidateInterceptor;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class UserConfig {

	/////////////////////////
	// DTO Converter //
	/////////////////////////

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

	////////////////////////////
	// ENTITY Converter //
	////////////////////////////

	@Bean
	public EntityUserConverter entityUserConverter() {
		return new EntityUserConverter();
	}

	@Bean
	public ConverterMapping entityUserConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserConverter());
		mapping.setTypeCode(User.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityUserFieldConverter entityUserFieldConverter() {
		return new EntityUserFieldConverter();
	}

	@Bean
	public ConverterMapping entityUserFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserFieldConverter());
		mapping.setTypeCode(UserField.class.getSimpleName());

		return mapping;
	}

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
	public EntityUserGroupFieldConverter entityUserGroupFieldConverter() {
		return new EntityUserGroupFieldConverter();
	}

	@Bean
	public ConverterMapping entityUserGroupFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserGroupFieldConverter());
		mapping.setTypeCode(UserGroupField.class.getSimpleName());

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
	public EntityUserPermissionFieldConverter entityUserPermissionFieldConverter() {
		return new EntityUserPermissionFieldConverter();
	}

	@Bean
	public ConverterMapping entityUserPermissionFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserPermissionFieldConverter());
		mapping.setTypeCode(UserPermissionField.class.getSimpleName());

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

	@Bean
	public EntityUserRightFieldConverter entityUserRightFieldConverter() {
		return new EntityUserRightFieldConverter();
	}

	@Bean
	public ConverterMapping entityUserRightFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserRightFieldConverter());
		mapping.setTypeCode(UserRightField.class.getSimpleName());

		return mapping;
	}

	////////////////////////////////////////
	// Initial Defaults Interceptor //
	////////////////////////////////////////

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

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public UserAuthorityValidateInterceptor userAuthorityValidateInterceptor() {
		return new UserAuthorityValidateInterceptor();
	}

	@Bean
	public InterceptorMapping userAuthorityValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(userAuthorityValidateInterceptor());
		interceptorMapping.setTypeCode(User.class.getSimpleName());

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
		interceptorMapping.setTypeCode(User.class.getSimpleName());

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
		interceptorMapping.setTypeCode(User.class.getSimpleName());

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
		interceptorMapping.setTypeCode(User.class.getSimpleName());

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

	///////////////////////////////
	// Prepare Interceptor //
	///////////////////////////////

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

	////////////////////////////
	// Load Interceptor //
	////////////////////////////

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

	//////////////////////////////
	// Remove Interceptor //
	//////////////////////////////

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

}
