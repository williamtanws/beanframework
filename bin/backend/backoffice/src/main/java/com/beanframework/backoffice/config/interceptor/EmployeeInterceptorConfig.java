package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.interceptor.EmployeeInitialDefaultsInterceptor;
import com.beanframework.employee.interceptor.EmployeeLoadInterceptor;
import com.beanframework.employee.interceptor.EmployeePrepareInterceptor;
import com.beanframework.employee.interceptor.EmployeeRemoveInterceptor;
import com.beanframework.employee.interceptor.EmployeeValidateInterceptor;

@Configuration
public class EmployeeInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public EmployeeInitialDefaultsInterceptor employeeInitialDefaultsInterceptor() {
		return new EmployeeInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping employeeInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(employeeInitialDefaultsInterceptor());
		mapping.setTypeCode(Employee.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public EmployeeLoadInterceptor employeeLoadInterceptor() {
		return new EmployeeLoadInterceptor();
	}

	@Bean
	public InterceptorMapping EmployeeLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(employeeLoadInterceptor());
		mapping.setTypeCode(Employee.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public EmployeePrepareInterceptor employeePrepareInterceptor() {
		return new EmployeePrepareInterceptor();
	}

	@Bean
	public InterceptorMapping EmployeePrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(employeePrepareInterceptor());
		mapping.setTypeCode(Employee.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public EmployeeValidateInterceptor employeeValidateInterceptor() {
		return new EmployeeValidateInterceptor();
	}

	@Bean
	public InterceptorMapping EmployeeValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(employeeValidateInterceptor());
		mapping.setTypeCode(Employee.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public EmployeeRemoveInterceptor employeeRemoveInterceptor() {
		return new EmployeeRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping EmployeeRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(employeeRemoveInterceptor());
		mapping.setTypeCode(Employee.class.getSimpleName());

		return mapping;
	}
}
