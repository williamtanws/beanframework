package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.employee.domain.Employee;

public class DtoEmployeeConverter implements DtoConverter<Employee, EmployeeDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoEmployeeConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public EmployeeDto convert(Employee source, ModelAction action) throws ConverterException {
		return convert(source, new EmployeeDto(), action);
	}

	public List<EmployeeDto> convert(List<Employee> sources, ModelAction action) throws ConverterException {
		List<EmployeeDto> convertedList = new ArrayList<EmployeeDto>();
		for (Employee source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private EmployeeDto convert(Employee source, EmployeeDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setPassword(source.getPassword());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		prototype.setName(source.getName());

		try {
			ModelAction disableInitialCollectionAction = new ModelAction();
			disableInitialCollectionAction.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionAction, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionAction, AuditorDto.class));

			if (action.isInitializeCollection()) {
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), action, UserGroupDto.class));
				prototype.setFields(modelService.getDto(source.getFields(), action, UserFieldDto.class));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
