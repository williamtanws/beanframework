package com.beanframework.backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.backoffice.converter.EntityCronjobConverter;
import com.beanframework.backoffice.converter.EntityCronjobDataConverter;
import com.beanframework.backoffice.converter.EntityCustomerConverter;
import com.beanframework.backoffice.converter.EntityDynamicFieldConverter;
import com.beanframework.backoffice.converter.EntityEmailConverter;
import com.beanframework.backoffice.converter.EntityEmployeeConverter;
import com.beanframework.backoffice.converter.EntityLanguageConverter;
import com.beanframework.backoffice.converter.EntityMenuConverter;
import com.beanframework.backoffice.converter.EntityUserGroupConverter;
import com.beanframework.backoffice.converter.EntityUserPermissionConverter;
import com.beanframework.backoffice.converter.EntityUserRightConverter;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;
import com.beanframework.customer.domain.Customer;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.email.domain.Email;
import com.beanframework.employee.domain.Employee;
import com.beanframework.language.domain.Language;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

@Configuration
public class BackofficeEntityConfig {

	@Bean
	public EntityLanguageConverter entityLanguageConverter() {
		return new EntityLanguageConverter();
	}

	@Bean
	public ConverterMapping entityLanguageConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityLanguageConverter());
		mapping.setTypeCode(Language.class.getSimpleName());

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
	public EntityMenuConverter entityMenuConverter() {
		return new EntityMenuConverter();
	}

	@Bean
	public ConverterMapping entityMenuConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityMenuConverter());
		mapping.setTypeCode(Menu.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCronjobConverter entityCronjobConverter() {
		return new EntityCronjobConverter();
	}

	@Bean
	public ConverterMapping entityCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCronjobConverter());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCronjobDataConverter entityCronjobDataConverter() {
		return new EntityCronjobDataConverter();
	}

	@Bean
	public ConverterMapping entityCronjobDataConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCronjobDataConverter());
		mapping.setTypeCode(CronjobData.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityDynamicFieldConverter entityDynamicFieldConverter() {
		return new EntityDynamicFieldConverter();
	}

	@Bean
	public ConverterMapping entityDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityDynamicFieldConverter());
		mapping.setTypeCode(DynamicField.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public EntityEmailConverter entityEmailConverter() {
		return new EntityEmailConverter();
	}

	@Bean
	public ConverterMapping entityEmailConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityEmailConverter());
		mapping.setTypeCode(Email.class.getSimpleName());

		return mapping;
	}
}
