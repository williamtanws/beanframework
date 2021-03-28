package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityCustomerConverter;
import com.beanframework.core.converter.entity.EntityEmployeeConverter;
import com.beanframework.core.converter.entity.EntityUserGroupConverter;
import com.beanframework.core.converter.entity.EntityUserPermissionConverter;
import com.beanframework.core.converter.entity.EntityUserRightConverter;
import com.beanframework.core.converter.entity.EntityVendorConverter;
import com.beanframework.user.domain.Customer;
import com.beanframework.user.domain.Employee;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.Vendor;

@Configuration
public class UserEntityConfig {

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
	public EntityEmployeeConverter entityEmployeeConverter() {
		return new EntityEmployeeConverter();
	}

	@Bean
	public ConverterMapping entityEmployeeConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityEmployeeConverter());
		mapping.setTypeCode(Employee.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCustomerConverter entityCustomerConverter() {
		return new EntityCustomerConverter();
	}

	@Bean
	public ConverterMapping entityCustomerConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCustomerConverter());
		mapping.setTypeCode(Customer.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public EntityVendorConverter entityVendorConverter() {
		return new EntityVendorConverter();
	}

	@Bean
	public ConverterMapping entityVendorConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityVendorConverter());
		mapping.setTypeCode(Vendor.class.getSimpleName());

		return mapping;
	}
}
