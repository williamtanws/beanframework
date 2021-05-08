package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.CoreConstants;
import com.beanframework.core.converter.entity.CustomerEntityConverter;
import com.beanframework.core.converter.entity.EmployeeEntityConverter;
import com.beanframework.core.converter.entity.MyAccountEntityConverter;
import com.beanframework.core.converter.entity.UserEntityConverter;
import com.beanframework.core.converter.entity.UserGroupEntityConverter;
import com.beanframework.core.converter.entity.UserPermissionEntityConverter;
import com.beanframework.core.converter.entity.UserRightEntityConverter;
import com.beanframework.core.converter.entity.VendorEntityConverter;
import com.beanframework.user.domain.Customer;
import com.beanframework.user.domain.Employee;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.Vendor;

@Configuration
public class UserEntityConfig {

	@Bean
	public UserGroupEntityConverter entityUserGroupConverter() {
		return new UserGroupEntityConverter();
	}

	@Bean
	public ConverterMapping entityUserGroupConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserGroupConverter());
		mapping.setTypeCode(UserGroup.class.getSimpleName());

		return mapping;
	}

	@Bean
	public UserPermissionEntityConverter entityUserPermissionConverter() {
		return new UserPermissionEntityConverter();
	}

	@Bean
	public ConverterMapping entityUserPermissionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserPermissionConverter());
		mapping.setTypeCode(UserPermission.class.getSimpleName());

		return mapping;
	}

	@Bean
	public UserRightEntityConverter entityUserRightConverter() {
		return new UserRightEntityConverter();
	}

	@Bean
	public ConverterMapping entityUserRightConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserRightConverter());
		mapping.setTypeCode(UserRight.class.getSimpleName());

		return mapping;
	}

	@Bean
	public MyAccountEntityConverter entityMyAccountConverter() {
		return new MyAccountEntityConverter();
	}

	@Bean
	public ConverterMapping entityMyAccountConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityMyAccountConverter());
		mapping.setTypeCode(CoreConstants.TypeCode.MYACCOUNT);

		return mapping;
	}

	@Bean
	public UserEntityConverter entityUserConverter() {
		return new UserEntityConverter();
	}

	@Bean
	public ConverterMapping entityUserConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityUserConverter());
		mapping.setTypeCode(User.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EmployeeEntityConverter entityEmployeeConverter() {
		return new EmployeeEntityConverter();
	}

	@Bean
	public ConverterMapping entityEmployeeConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityEmployeeConverter());
		mapping.setTypeCode(Employee.class.getSimpleName());

		return mapping;
	}

	@Bean
	public CustomerEntityConverter entityCustomerConverter() {
		return new CustomerEntityConverter();
	}

	@Bean
	public ConverterMapping entityCustomerConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCustomerConverter());
		mapping.setTypeCode(Customer.class.getSimpleName());

		return mapping;
	}

	@Bean
	public VendorEntityConverter entityVendorConverter() {
		return new VendorEntityConverter();
	}

	@Bean
	public ConverterMapping entityVendorConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityVendorConverter());
		mapping.setTypeCode(Vendor.class.getSimpleName());

		return mapping;
	}
}
