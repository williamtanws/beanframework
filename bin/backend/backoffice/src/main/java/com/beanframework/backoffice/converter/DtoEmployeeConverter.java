package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.backoffice.data.EmployeeDto;
import com.beanframework.backoffice.data.UserFieldDto;
import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.domain.Employee;

public class DtoEmployeeConverter implements DtoConverter<Employee, EmployeeDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoEmployeeConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public EmployeeDto convert(Employee source) throws ConverterException {
		return convert(source, new EmployeeDto());
	}

	public List<EmployeeDto> convert(List<Employee> sources) throws ConverterException {
		List<EmployeeDto> convertedList = new ArrayList<EmployeeDto>();
		for (Employee source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private EmployeeDto convert(Employee source, EmployeeDto prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setPassword(source.getPassword());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		prototype.setName(source.getName());
		try {
			prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class));
			prototype.setFields(modelService.getDto(source.getFields(), UserFieldDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
