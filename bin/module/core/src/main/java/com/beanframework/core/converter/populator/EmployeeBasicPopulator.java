package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.user.domain.Employee;

@Component
public class EmployeeBasicPopulator extends AbstractPopulator<Employee, EmployeeDto> implements Populator<Employee, EmployeeDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EmployeeBasicPopulator.class);

	@Override
	public void populate(Employee source, EmployeeDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
