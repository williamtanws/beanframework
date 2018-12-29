package com.beanframework.employee.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.domain.Employee;

public class EmployeeValidateInterceptor implements ValidateInterceptor<Employee> {
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(Employee model) throws InterceptorException {
		
		try {
			if (model.getUuid() == null) {
				// Save new
				if (StringUtils.isBlank(model.getId())) {
					throw new InterceptorException(localMessageService.getMessage(EmployeeConstants.Locale.ID_REQUIRED), this);
				} else if (StringUtils.isBlank(model.getPassword())) {
					throw new InterceptorException(localMessageService.getMessage(EmployeeConstants.Locale.PASSWORD_REQUIRED),
							this);
				} else {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(Employee.ID, model.getId());
					boolean exists = modelService.existsByProperties(properties, Employee.class);
					if (exists) {
						throw new InterceptorException(localMessageService.getMessage(EmployeeConstants.Locale.ID_EXISTS),
								this);
					}
				}

			} else {
				// Update exists
				if (StringUtils.isNotBlank(model.getId())) {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(Employee.ID, model.getId());
					Employee exists = modelService.findOneEntityByProperties(properties, Employee.class);
					if (exists != null) {
						if (!model.getUuid().equals(exists.getUuid())) {
							throw new InterceptorException(localMessageService.getMessage(EmployeeConstants.Locale.ID_EXISTS),
									this);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
