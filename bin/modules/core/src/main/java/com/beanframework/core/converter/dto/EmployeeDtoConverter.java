package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.user.domain.Employee;

public class EmployeeDtoConverter extends AbstractDtoConverter<Employee, EmployeeDto> implements DtoConverter<Employee, EmployeeDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EmployeeDtoConverter.class);

	@Override
	public EmployeeDto convert(Employee source) throws ConverterException {
		return super.convert(source, new EmployeeDto());
	}
}
