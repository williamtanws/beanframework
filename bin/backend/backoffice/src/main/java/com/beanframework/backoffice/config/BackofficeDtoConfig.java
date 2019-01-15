package com.beanframework.backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.backoffice.converter.DtoAuditorConverter;
import com.beanframework.backoffice.converter.DtoBackofficeConfigurationConverter;
import com.beanframework.backoffice.converter.DtoCronjobConverter;
import com.beanframework.backoffice.converter.DtoCronjobDataConverter;
import com.beanframework.backoffice.converter.DtoCustomerConverter;
import com.beanframework.backoffice.converter.DtoDynamicFieldConverter;
import com.beanframework.backoffice.converter.DtoDynamicFieldEnumValueConverter;
import com.beanframework.backoffice.converter.DtoEmailConverter;
import com.beanframework.backoffice.converter.DtoEmployeeConverter;
import com.beanframework.backoffice.converter.DtoLanguageConverter;
import com.beanframework.backoffice.converter.DtoMenuConverter;
import com.beanframework.backoffice.converter.DtoMenuFieldConverter;
import com.beanframework.backoffice.converter.DtoUserAuthorityConverter;
import com.beanframework.backoffice.converter.DtoUserFieldConverter;
import com.beanframework.backoffice.converter.DtoUserGroupConverter;
import com.beanframework.backoffice.converter.DtoUserGroupFieldConverter;
import com.beanframework.backoffice.converter.DtoUserPermissionConverter;
import com.beanframework.backoffice.converter.DtoUserPermissionFieldConverter;
import com.beanframework.backoffice.converter.DtoUserRightConverter;
import com.beanframework.backoffice.converter.DtoUserRightFieldConverter;
import com.beanframework.backoffice.data.AuditorDto;
import com.beanframework.backoffice.data.BackofficeConfigurationDto;
import com.beanframework.backoffice.data.CronjobDataDto;
import com.beanframework.backoffice.data.CronjobDto;
import com.beanframework.backoffice.data.CustomerDto;
import com.beanframework.backoffice.data.DynamicFieldDto;
import com.beanframework.backoffice.data.DynamicFieldEnumValueDto;
import com.beanframework.backoffice.data.EmailDto;
import com.beanframework.backoffice.data.EmployeeDto;
import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.backoffice.data.MenuDto;
import com.beanframework.backoffice.data.MenuFieldDto;
import com.beanframework.backoffice.data.UserAuthorityDto;
import com.beanframework.backoffice.data.UserFieldDto;
import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.backoffice.data.UserGroupFieldDto;
import com.beanframework.backoffice.data.UserPermissionDto;
import com.beanframework.backoffice.data.UserPermissionFieldDto;
import com.beanframework.backoffice.data.UserRightDto;
import com.beanframework.backoffice.data.UserRightFieldDto;
import com.beanframework.common.converter.ConverterMapping;

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
	public DtoDynamicFieldEnumValueConverter dtoDynamicFieldEnumValueConverter() {
		return new DtoDynamicFieldEnumValueConverter();
	}

	@Bean
	public ConverterMapping dtoDynamicFieldEnumValueConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldEnumValueConverter());
		mapping.setTypeCode(DynamicFieldEnumValueDto.class.getSimpleName());

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
}
