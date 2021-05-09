package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.core.converter.dto.AuditorDtoConverter;
import com.beanframework.core.converter.dto.CustomerDtoConverter;
import com.beanframework.core.converter.dto.EmployeeDtoConverter;
import com.beanframework.core.converter.dto.MyAccountDtoConverter;
import com.beanframework.core.converter.dto.RevisionsDtoConverter;
import com.beanframework.core.converter.dto.UserAuthorityDtoConverter;
import com.beanframework.core.converter.dto.UserDtoConverter;
import com.beanframework.core.converter.dto.UserGroupDtoConverter;
import com.beanframework.core.converter.dto.UserPermissionDtoConverter;
import com.beanframework.core.converter.dto.UserRightDtoConverter;
import com.beanframework.core.converter.dto.VendorDtoConverter;
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

	@Autowired
	public AuditorPopulator auditorPopulator;

	@Autowired
	public UserPopulator userPopulator;

	@Autowired
	public UserGroupPopulator userGroupPopulator;

	@Autowired
	public UserPermissionPopulator userPermissionPopulator;

	@Autowired
	public UserRightPopulator userRightPopulator;

	@Autowired
	public UserAuthorityPopulator userAuthorityPopulator;

	@Autowired
	public MyAccountPopulator myAccountPopulator;

	@Autowired
	public EmployeePopulator employeePopulator;

	@Autowired
	public CustomerPopulator customerPopulator;

	@Autowired
	public VendorPopulator vendorPopulator;

	// Revision
	@Bean
	public RevisionsDtoConverter revisionsDtoConverter() {
		RevisionsDtoConverter converter = new RevisionsDtoConverter();
		return converter;
	}

	// Auditor
	@Bean
	public AuditorDtoConverter auditorDtoConverter() {
		AuditorDtoConverter converter = new AuditorDtoConverter();
		converter.addPopulator(auditorPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping auditorDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(auditorDtoConverter());
		mapping.setTypeCode(AuditorDto.class.getSimpleName());

		return mapping;
	}

	// User
	@Bean
	public UserDtoConverter userDtoConverter() {
		UserDtoConverter converter = new UserDtoConverter();
		converter.addPopulator(userPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping userDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(userDtoConverter());
		mapping.setTypeCode(UserDto.class.getSimpleName());

		return mapping;
	}

	// User Group
	@Bean
	public UserGroupDtoConverter userGroupDtoConverter() {
		UserGroupDtoConverter converter = new UserGroupDtoConverter();
		converter.addPopulator(userGroupPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping userGroupDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(userGroupDtoConverter());
		mapping.setTypeCode(UserGroupDto.class.getSimpleName());

		return mapping;
	}

	// User Permission
	@Bean
	public UserPermissionDtoConverter uerPermissionDtoConverter() {
		UserPermissionDtoConverter converter = new UserPermissionDtoConverter();
		converter.addPopulator(userPermissionPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping uerPermissionDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(uerPermissionDtoConverter());
		mapping.setTypeCode(UserPermissionDto.class.getSimpleName());

		return mapping;
	}

	// User Right
	@Bean
	public UserRightDtoConverter userRightDtoConverter() {
		UserRightDtoConverter converter = new UserRightDtoConverter();
		converter.addPopulator(userRightPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping userRightDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(userRightDtoConverter());
		mapping.setTypeCode(UserRightDto.class.getSimpleName());

		return mapping;
	}

	// User Authority
	@Bean
	public UserAuthorityDtoConverter userAuthorityDtoConverter() {
		UserAuthorityDtoConverter converter = new UserAuthorityDtoConverter();
		converter.addPopulator(userAuthorityPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping userAuthorityDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(userAuthorityDtoConverter());
		mapping.setTypeCode(UserAuthorityDto.class.getSimpleName());

		return mapping;
	}

	// My Account
	@Bean
	public MyAccountDtoConverter myAccountDtoConverter() {
		MyAccountDtoConverter converter = new MyAccountDtoConverter();
		converter.addPopulator(myAccountPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping myAccountDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(myAccountDtoConverter());
		mapping.setTypeCode(MyAccountDto.class.getSimpleName());

		return mapping;
	}

	// Employee
	@Bean
	public EmployeeDtoConverter employeeDtoConverter() {
		EmployeeDtoConverter converter = new EmployeeDtoConverter();
		converter.addPopulator(employeePopulator);
		return converter;
	}

	@Bean
	public ConverterMapping employeeDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(employeeDtoConverter());
		mapping.setTypeCode(EmployeeDto.class.getSimpleName());

		return mapping;
	}

	// Customer
	@Bean
	public CustomerDtoConverter customerDtoConverter() {
		CustomerDtoConverter converter = new CustomerDtoConverter();
		converter.addPopulator(customerPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping customerDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(customerDtoConverter());
		mapping.setTypeCode(CustomerDto.class.getSimpleName());

		return mapping;
	}

	// Vendor
	@Bean
	public VendorDtoConverter vendorDtoConverter() {
		VendorDtoConverter converter = new VendorDtoConverter();
		converter.addPopulator(vendorPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping vendorDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(vendorDtoConverter());
		mapping.setTypeCode(VendorDto.class.getSimpleName());

		return mapping;
	}

}
