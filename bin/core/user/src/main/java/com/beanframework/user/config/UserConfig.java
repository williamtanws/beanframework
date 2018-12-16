package com.beanframework.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.user.converter.DtoUserAuthorityConverter;
import com.beanframework.user.converter.DtoUserConverter;
import com.beanframework.user.converter.DtoUserGroupConverter;
import com.beanframework.user.converter.DtoUserGroupLangConverter;
import com.beanframework.user.converter.DtoUserPermissionConverter;
import com.beanframework.user.converter.DtoUserPermissionLangConverter;
import com.beanframework.user.converter.DtoUserRightConverter;
import com.beanframework.user.converter.DtoUserRightLangConverter;
import com.beanframework.user.converter.EntityUserConverter;
import com.beanframework.user.converter.EntityUserGroupConverter;
import com.beanframework.user.converter.EntityUserPermissionConverter;
import com.beanframework.user.converter.EntityUserRightConverter;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupLang;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionLang;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightLang;
import com.beanframework.user.interceptor.UserInitialDefaultsInterceptor;
import com.beanframework.user.interceptor.UserLoadInterceptor;
import com.beanframework.user.interceptor.UserPrepareInterceptor;
import com.beanframework.user.interceptor.UserRemoveInterceptor;
import com.beanframework.user.interceptor.UserValidateInterceptor;

@Configuration
public class UserConfig {

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
	public DtoUserConverter dtoUserConverter() {
		return new DtoUserConverter();
	}

	@Bean
	public ConverterMapping dtoUserConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserConverter());
		mapping.setTypeCode(User.class.getSimpleName());

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
	public DtoUserGroupLangConverter dtoUserGroupLangConverter() {
		return new DtoUserGroupLangConverter();
	}

	@Bean
	public ConverterMapping dtoUserGroupLangConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserGroupLangConverter());
		mapping.setTypeCode(UserGroupLang.class.getSimpleName());

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
	public DtoUserPermissionLangConverter dtoUserPermissionLangConverter() {
		return new DtoUserPermissionLangConverter();
	}

	@Bean
	public ConverterMapping dtoUserPermissionLangConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserPermissionLangConverter());
		mapping.setTypeCode(UserPermissionLang.class.getSimpleName());

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
	public DtoUserRightLangConverter dtoUserRightLangConverter() {
		return new DtoUserRightLangConverter();
	}

	@Bean
	public ConverterMapping DtoUserRightLangConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserRightLangConverter());
		mapping.setTypeCode(UserRightLang.class.getSimpleName());

		return mapping;
	}

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
