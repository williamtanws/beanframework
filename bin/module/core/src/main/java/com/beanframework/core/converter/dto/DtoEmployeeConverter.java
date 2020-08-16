package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.user.domain.Employee;

public class DtoEmployeeConverter extends AbstractDtoConverter<Employee, EmployeeDto> implements DtoConverter<Employee, EmployeeDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoEmployeeConverter.class);

	@Override
	public EmployeeDto convert(Employee source, DtoConverterContext context) throws ConverterException {
		try {
			EmployeeDto target = new EmployeeDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<EmployeeDto> convert(List<Employee> sources, DtoConverterContext context) throws ConverterException {
		List<EmployeeDto> convertedList = new ArrayList<EmployeeDto>();
		for (Employee source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}
}
