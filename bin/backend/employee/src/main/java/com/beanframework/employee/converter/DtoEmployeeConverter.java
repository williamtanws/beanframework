package com.beanframework.employee.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.domain.Employee;

public class DtoEmployeeConverter implements DtoConverter<Employee, Employee> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DtoEmployeeConverter.class);
	
	@Autowired
	private ModelService modelService;

	@Override
	public Employee convert(Employee source) throws ConverterException {
		return convert(source, new Employee());
	}

	public List<Employee> convert(List<Employee> sources) throws ConverterException {
		List<Employee> convertedList = new ArrayList<Employee>();
		for (Employee source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Employee convert(Employee source, Employee prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
				
		prototype.setAuthorities(source.getAuthorities());
		prototype.setPassword(source.getPassword());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		try {
			prototype.setUserGroups(modelService.getDto(source.getUserGroups()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
