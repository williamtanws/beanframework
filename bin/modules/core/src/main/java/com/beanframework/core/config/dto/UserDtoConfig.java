package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.core.converter.dto.DtoAuditorConverter;
import com.beanframework.core.converter.dto.DtoCustomerConverter;
import com.beanframework.core.converter.dto.DtoEmployeeConverter;
import com.beanframework.core.converter.dto.DtoMyAccountConverter;
import com.beanframework.core.converter.dto.DtoRevisionsConverter;
import com.beanframework.core.converter.dto.DtoUserAuthorityConverter;
import com.beanframework.core.converter.dto.DtoUserConverter;
import com.beanframework.core.converter.dto.DtoUserGroupConverter;
import com.beanframework.core.converter.dto.DtoUserPermissionConverter;
import com.beanframework.core.converter.dto.DtoUserRightConverter;
import com.beanframework.core.converter.dto.DtoVendorConverter;
import com.beanframework.core.converter.populator.AuditorPopulator;
import com.beanframework.core.converter.populator.CustomerPopulator;
import com.beanframework.core.converter.populator.EmployeePopulator;
import com.beanframework.core.converter.populator.MyAccountPopulator;
import com.beanframework.core.converter.populator.UserAuthorityPopulator;
import com.beanframework.core.converter.populator.UserGroupPopulator;
import com.beanframework.core.converter.populator.UserPermissionPopulator;
import com.beanframework.core.converter.populator.UserPopulator;
import com.beanframework.core.converter.populator.UserRightPopulator;
import com.beanframework.core.converter.populator.VendorPopulator;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.data.MyAccountDto;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.VendorDto;

@Configuration
public class UserDtoConfig {

	// Revision
	@Bean
	public DtoRevisionsConverter dtoRevisionsConverter() {
		DtoRevisionsConverter converter = new DtoRevisionsConverter();
		return converter;
	}

	// Auditor
	@Bean
	public AuditorPopulator auditorPopulator() {
		return new AuditorPopulator();
	}

	@Bean
	public DtoAuditorConverter dtoAuditorConverter() {
		DtoAuditorConverter converter = new DtoAuditorConverter();
		converter.addPopulator(auditorPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoAuditorConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoAuditorConverter());
		mapping.setTypeCode(AuditorDto.class.getSimpleName());

		return mapping;
	}

	// User
	@Bean
	public UserPopulator userPopulator() {
		return new UserPopulator();
	}

	@Bean
	public DtoUserConverter dtoUserConverter() {
		DtoUserConverter converter = new DtoUserConverter();
		converter.addPopulator(userPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoUserConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserConverter());
		mapping.setTypeCode(UserDto.class.getSimpleName());

		return mapping;
	}

	// User Group
	@Bean
	public UserGroupPopulator userGroupPopulator() {
		return new UserGroupPopulator();
	}

	@Bean
	public DtoUserGroupConverter dtoUserGroupConverter() {
		DtoUserGroupConverter converter = new DtoUserGroupConverter();
		converter.addPopulator(userGroupPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoUserGroupConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserGroupConverter());
		mapping.setTypeCode(UserGroupDto.class.getSimpleName());

		return mapping;
	}

	// User Permission
	@Bean
	public UserPermissionPopulator userPermissionPopulator() {
		return new UserPermissionPopulator();
	}

	@Bean
	public DtoUserPermissionConverter dtoUserPermissionConverter() {
		DtoUserPermissionConverter converter = new DtoUserPermissionConverter();
		converter.addPopulator(userPermissionPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoUserPermissionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserPermissionConverter());
		mapping.setTypeCode(UserPermissionDto.class.getSimpleName());

		return mapping;
	}

	// User Right
	@Bean
	public UserRightPopulator userRightPopulator() {
		return new UserRightPopulator();
	}

	@Bean
	public DtoUserRightConverter dtoUserRightConverter() {
		DtoUserRightConverter converter = new DtoUserRightConverter();
		converter.addPopulator(userRightPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoUserRightConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserRightConverter());
		mapping.setTypeCode(UserRightDto.class.getSimpleName());

		return mapping;
	}

	// User Authority
	@Bean
	public UserAuthorityPopulator userAuthorityPopulator() {
		return new UserAuthorityPopulator();
	}

	@Bean
	public DtoUserAuthorityConverter dtoUserAuthorityConverter() {
		DtoUserAuthorityConverter converter = new DtoUserAuthorityConverter();
		converter.addPopulator(userAuthorityPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoUserAuthorityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoUserAuthorityConverter());
		mapping.setTypeCode(UserAuthorityDto.class.getSimpleName());

		return mapping;
	}
	
	// My Account
	@Bean
	public MyAccountPopulator myAccountPopulator() {
		return new MyAccountPopulator();
	}

	@Bean
	public DtoMyAccountConverter dtoMyAccountConverter() {
		DtoMyAccountConverter converter = new DtoMyAccountConverter();
		converter.addPopulator(myAccountPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoMyAccountConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoEmployeeConverter());
		mapping.setTypeCode(MyAccountDto.class.getSimpleName());

		return mapping;
	}

	// Employee
	@Bean
	public EmployeePopulator employeePopulator() {
		return new EmployeePopulator();
	}

	@Bean
	public DtoEmployeeConverter dtoEmployeeConverter() {
		DtoEmployeeConverter converter = new DtoEmployeeConverter();
		converter.addPopulator(employeePopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoEmployeeConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoEmployeeConverter());
		mapping.setTypeCode(EmployeeDto.class.getSimpleName());

		return mapping;
	}

	// Customer
	@Bean
	public CustomerPopulator customerPopulator() {
		return new CustomerPopulator();
	}

	@Bean
	public DtoCustomerConverter dtoCustomerConverter() {
		DtoCustomerConverter converter = new DtoCustomerConverter();
		converter.addPopulator(customerPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoCustomerConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCustomerConverter());
		mapping.setTypeCode(CustomerDto.class.getSimpleName());

		return mapping;
	}

	// Vendor
	@Bean
	public VendorPopulator vendorPopulator() {
		return new VendorPopulator();
	}

	@Bean
	public DtoVendorConverter dtoVendorConverter() {
		DtoVendorConverter converter = new DtoVendorConverter();
		converter.addPopulator(vendorPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoVendorConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoVendorConverter());
		mapping.setTypeCode(VendorDto.class.getSimpleName());

		return mapping;
	}

}
