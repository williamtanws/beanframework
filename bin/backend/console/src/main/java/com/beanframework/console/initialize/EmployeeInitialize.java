package com.beanframework.console.initialize;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.Initializer;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.WebPlatformConstants.Initialize.Employee;
import com.beanframework.employee.service.EmployeeFacade;

public class EmployeeInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(EmployeeInitialize.class);

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private EmployeeFacade employeeFacade;

	@PostConstruct
	public void initializer() {
		setKey(WebPlatformConstants.Initialize.Employee.KEY);
		setName(WebPlatformConstants.Initialize.Employee.NAME);
		setSort(WebPlatformConstants.Initialize.Employee.SORT);
		setDescription(WebPlatformConstants.Initialize.Employee.DESCRIPTION);
	}

	@Override
	public void initialize() {
		modelService.removeAll(Employee.class);
		employeeFacade.deleteAllEmployeeProfilePicture();
	}

}
