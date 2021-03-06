package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.employee.EmployeeInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.employee.EmployeeLoadInterceptor;
import com.beanframework.core.interceptor.employee.EmployeePrepareInterceptor;
import com.beanframework.user.domain.Employee;

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
  public InterceptorMapping employeeLoadInterceptorMapping() {
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
  public InterceptorMapping employeePrepareInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(employeePrepareInterceptor());
    mapping.setTypeCode(Employee.class.getSimpleName());

    return mapping;
  }
}
