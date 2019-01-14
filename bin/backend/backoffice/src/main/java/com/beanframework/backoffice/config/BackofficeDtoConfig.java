package com.beanframework.backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.backoffice.converter.DtoAuditorConverter;
import com.beanframework.backoffice.converter.DtoUserAuthorityConverter;
import com.beanframework.backoffice.converter.DtoUserFieldConverter;
import com.beanframework.backoffice.converter.DtoUserGroupConverter;
import com.beanframework.backoffice.converter.DtoUserGroupFieldConverter;
import com.beanframework.backoffice.converter.DtoUserPermissionConverter;
import com.beanframework.backoffice.converter.DtoUserPermissionFieldConverter;
import com.beanframework.backoffice.converter.DtoUserRightConverter;
import com.beanframework.backoffice.converter.DtoUserRightFieldConverter;
import com.beanframework.backoffice.data.AuditorDto;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

@Configuration
public class BackofficeDtoConfig {

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
		mapping.setTypeCode(AuditorDto.class.getSimpleName());

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
}
