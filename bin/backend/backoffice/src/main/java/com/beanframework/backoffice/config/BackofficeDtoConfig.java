package com.beanframework.backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.backoffice.converter.DtoAuditorConverter;
import com.beanframework.backoffice.converter.DtoBackofficeConfigurationConverter;
import com.beanframework.backoffice.converter.DtoCommentConverter;
import com.beanframework.backoffice.converter.DtoCronjobConverter;
import com.beanframework.backoffice.converter.DtoCronjobDataConverter;
import com.beanframework.backoffice.converter.DtoCustomerConverter;
import com.beanframework.backoffice.converter.DtoDynamicFieldConverter;
import com.beanframework.backoffice.converter.DtoDynamicFieldEnumConverter;
import com.beanframework.backoffice.converter.DtoEmailConverter;
import com.beanframework.backoffice.converter.DtoEmployeeConverter;
import com.beanframework.backoffice.converter.DtoLanguageConverter;
import com.beanframework.backoffice.converter.DtoMenuConverter;
import com.beanframework.backoffice.converter.DtoMenuFieldConverter;
import com.beanframework.backoffice.converter.DtoUserAuthorityConverter;
import com.beanframework.backoffice.converter.DtoUserConverter;
import com.beanframework.backoffice.converter.DtoUserFieldConverter;
import com.beanframework.backoffice.converter.DtoUserGroupConverter;
import com.beanframework.backoffice.converter.DtoUserGroupFieldConverter;
import com.beanframework.backoffice.converter.DtoUserPermissionConverter;
import com.beanframework.backoffice.converter.DtoUserPermissionFieldConverter;
import com.beanframework.backoffice.converter.DtoUserRightConverter;
import com.beanframework.backoffice.converter.DtoUserRightFieldConverter;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.data.AuditorDto;
import com.beanframework.core.data.BackofficeConfigurationDto;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.DynamicFieldEnumDto;
import com.beanframework.core.data.EmailDto;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserGroupFieldDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightFieldDto;

@Configuration
public class BackofficeDtoConfig {
	
	@Bean
	public DtoBackofficeConfigurationConverter dtoBackofficeConfigurationConverter() {
		return new DtoBackofficeConfigurationConverter();
	}

	@Bean
	public ConverterMapping dtoBackofficeConfigurationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoBackofficeConfigurationConverter());
		mapping.setTypeCode(BackofficeConfigurationDto.class.getSimpleName());

		return mapping;
	}
	
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
		mapping.setTypeCode(UserAuthorityDto.class.getSimpleName());

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
		mapping.setTypeCode(UserFieldDto.class.getSimpleName());

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
		mapping.setTypeCode(UserGroupDto.class.getSimpleName());

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
		mapping.setTypeCode(UserGroupFieldDto.class.getSimpleName());

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
		mapping.setTypeCode(UserPermissionDto.class.getSimpleName());

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
		mapping.setTypeCode(UserPermissionFieldDto.class.getSimpleName());

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
		mapping.setTypeCode(UserRightDto.class.getSimpleName());

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
		mapping.setTypeCode(UserRightFieldDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoLanguageConverter dtoLanguageConverter() {
		return new DtoLanguageConverter();
	}

	@Bean
	public ConverterMapping dtoLanguageConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoLanguageConverter());
		mapping.setTypeCode(LanguageDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoCustomerConverter dtoCustomerConverter() {
		return new DtoCustomerConverter();
	}

	@Bean
	public ConverterMapping dtoCustomerConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCustomerConverter());
		mapping.setTypeCode(CustomerDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoEmployeeConverter dtoEmployeeConverter() {
		return new DtoEmployeeConverter();
	}

	@Bean
	public ConverterMapping dtoEmployeeConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoEmployeeConverter());
		mapping.setTypeCode(EmployeeDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoMenuConverter dtoMenuConverter() {
		return new DtoMenuConverter();
	}

	@Bean
	public ConverterMapping dtoMenuConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoMenuConverter());
		mapping.setTypeCode(MenuDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoMenuFieldConverter dtoMenuFieldConverter() {
		return new DtoMenuFieldConverter();
	}

	@Bean
	public ConverterMapping dtoMenuFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoMenuFieldConverter());
		mapping.setTypeCode(MenuFieldDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoCronjobConverter dtoCronjobConverter() {
		return new DtoCronjobConverter();
	}

	@Bean
	public ConverterMapping dtoCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCronjobConverter());
		mapping.setTypeCode(CronjobDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoCronjobDataConverter dtoCronjobDataConverter() {
		return new DtoCronjobDataConverter();
	}

	@Bean
	public ConverterMapping dtoCronjobDataConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCronjobDataConverter());
		mapping.setTypeCode(CronjobDataDto.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public DtoDynamicFieldEnumConverter dtoDynamicFieldEnumConverter() {
		return new DtoDynamicFieldEnumConverter();
	}

	@Bean
	public ConverterMapping dtoDynamicFieldEnumConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldEnumConverter());
		mapping.setTypeCode(DynamicFieldEnumDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoDynamicFieldConverter dtoDynamicFieldConverter() {
		return new DtoDynamicFieldConverter();
	}

	@Bean
	public ConverterMapping dtoDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldConverter());
		mapping.setTypeCode(DynamicFieldDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoDynamicFieldEnumConverter dtoDynamicFieldEnumValueConverter() {
		return new DtoDynamicFieldEnumConverter();
	}

	@Bean
	public ConverterMapping dtoDynamicFieldEnumValueConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldEnumValueConverter());
		mapping.setTypeCode(DynamicFieldEnumDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoEmailConverter dtoEmailConverter() {
		return new DtoEmailConverter();
	}

	@Bean
	public ConverterMapping dtoEmailConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoEmailConverter());
		mapping.setTypeCode(EmailDto.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public DtoCommentConverter dtoCommentConverter() {
		return new DtoCommentConverter();
	}

	@Bean
	public ConverterMapping DtoCommentConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCommentConverter());
		mapping.setTypeCode(CommentDto.class.getSimpleName());

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
		mapping.setTypeCode(UserDto.class.getSimpleName());

		return mapping;
	}
}
