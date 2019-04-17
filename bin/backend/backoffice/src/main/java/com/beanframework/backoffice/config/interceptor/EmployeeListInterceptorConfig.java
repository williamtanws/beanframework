package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.interceptor.EmployeeListLoadInterceptor;

@Configuration
public class EmployeeListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public EmployeeListLoadInterceptor employeeListLoadInterceptor() {
		return new EmployeeListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping EmployeeListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(employeeListLoadInterceptor());
		mapping.setTypeCode(Employee.class.getSimpleName() + ModelService.DEFAULT_LIST_LOAD_INTERCEPTOR_POSTFIX);

		return mapping;
	}
}
