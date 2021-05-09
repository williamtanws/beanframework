package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public UserGroupEntityConverter userGroupEntityConverter;

	@Autowired
	public UserPermissionEntityConverter userPermissionEntityConverter;

	@Autowired
	public UserRightEntityConverter userRightEntityConverter;

	@Autowired
	public MyAccountEntityConverter myAccountEntityConverter;

	@Autowired
	public UserEntityConverter userEntityConverter;

	@Autowired
	public EmployeeEntityConverter employeeEntityConverter;

	@Autowired
	public CustomerEntityConverter customerEntityConverter;

	@Autowired
	public VendorEntityConverter vendorEntityConverter;

	@Bean
	public ConverterMapping userGroupEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(userGroupEntityConverter);
		mapping.setTypeCode(UserGroup.class.getSimpleName());

		return mapping;
	}

	@Bean
	public ConverterMapping userPermissionEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(userPermissionEntityConverter);
		mapping.setTypeCode(UserPermission.class.getSimpleName());

		return mapping;
	}

	@Bean
	public ConverterMapping userRightEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(userRightEntityConverter);
		mapping.setTypeCode(UserRight.class.getSimpleName());

		return mapping;
	}

	@Bean
	public ConverterMapping myAccountEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(myAccountEntityConverter);
		mapping.setTypeCode(CoreConstants.TypeCode.MYACCOUNT);

		return mapping;
	}

	@Bean
	public ConverterMapping userEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(userEntityConverter);
		mapping.setTypeCode(User.class.getSimpleName());

		return mapping;
	}

	@Bean
	public ConverterMapping employeeEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(employeeEntityConverter);
		mapping.setTypeCode(Employee.class.getSimpleName());

		return mapping;
	}

	@Bean
	public ConverterMapping customerEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(customerEntityConverter);
		mapping.setTypeCode(Customer.class.getSimpleName());

		return mapping;
	}

	@Bean
	public ConverterMapping vendorEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(vendorEntityConverter);
		mapping.setTypeCode(Vendor.class.getSimpleName());

		return mapping;
	}
}
