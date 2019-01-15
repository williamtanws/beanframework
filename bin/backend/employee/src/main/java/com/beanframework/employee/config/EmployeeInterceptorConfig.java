package com.beanframework.employee.config;

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

	@Bean
	public EmployeeInitialDefaultsInterceptor employeeInitialDefaultsInterceptor() {
		return new EmployeeInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping employeeInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(employeeInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(Employee.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public EmployeeValidateInterceptor employeeValidateInterceptor() {
		return new EmployeeValidateInterceptor();
	}

	@Bean
	public InterceptorMapping employeeValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(employeeValidateInterceptor());
		interceptorMapping.setTypeCode(Employee.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public EmployeePrepareInterceptor employeePrepareInterceptor() {
		return new EmployeePrepareInterceptor();
	}

	@Bean
	public InterceptorMapping employeePrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(employeePrepareInterceptor());
		interceptorMapping.setTypeCode(Employee.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public EmployeeLoadInterceptor employeeLoadInterceptor() {
		return new EmployeeLoadInterceptor();
	}

	@Bean
	public InterceptorMapping employeeLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(employeeLoadInterceptor());
		interceptorMapping.setTypeCode(Employee.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public EmployeeRemoveInterceptor employeeRemoveInterceptor() {
		return new EmployeeRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping employeeRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(employeeRemoveInterceptor());
		interceptorMapping.setTypeCode(Employee.class.getSimpleName());

		return interceptorMapping;
	}

}
