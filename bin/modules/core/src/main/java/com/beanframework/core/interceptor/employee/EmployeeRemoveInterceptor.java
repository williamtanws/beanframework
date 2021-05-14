package com.beanframework.core.interceptor.employee;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.user.domain.Employee;

public class EmployeeRemoveInterceptor extends AbstractRemoveInterceptor<Employee> {

  @Override
  public void onRemove(Employee model, InterceptorContext context) throws InterceptorException {}

}
