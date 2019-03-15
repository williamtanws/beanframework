package com.beanframework.backoffice.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.EntityCustomerConverter;
import com.beanframework.core.converter.EntityEmployeeConverter;
import com.beanframework.core.converter.EntityUserGroupConverter;
import com.beanframework.core.converter.EntityUserPermissionConverter;
import com.beanframework.core.converter.EntityUserRightConverter;
import com.beanframework.customer.domain.Customer;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

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
}
