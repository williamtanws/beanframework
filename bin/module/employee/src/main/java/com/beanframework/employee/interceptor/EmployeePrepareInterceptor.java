package com.beanframework.employee.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.employee.domain.Employee;

public class EmployeePrepareInterceptor implements PrepareInterceptor<Employee> {

	@Override
	public void onPrepare(Employee model, InterceptorContext context) throws InterceptorException {

		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}
}
