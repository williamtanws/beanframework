package com.beanframework.core.interceptor.employee;

import org.apache.commons.lang3.StringUtils;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.user.domain.Employee;

public class EmployeePrepareInterceptor extends AbstractPrepareInterceptor<Employee> {

  @Override
  public void onPrepare(Employee model, InterceptorContext context) throws InterceptorException {
    super.onPrepare(model, context);

    for (int i = 0; i < model.getAttributes().size(); i++) {
      if (StringUtils.isBlank(model.getAttributes().get(i).getValue())) {
        model.getAttributes().get(i).setValue(null);
      }
    }
  }
}
