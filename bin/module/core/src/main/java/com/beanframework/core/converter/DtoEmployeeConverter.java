package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.employee.domain.Employee;

public class DtoEmployeeConverter extends AbstractDtoConverter<Employee, EmployeeDto> implements DtoConverter<Employee, EmployeeDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoEmployeeConverter.class);

	@Override
	public EmployeeDto convert(Employee source, DtoConverterContext context) throws ConverterException {
		return convert(source, new EmployeeDto(), context);
	}

	public List<EmployeeDto> convert(List<Employee> sources, DtoConverterContext context) throws ConverterException {
		List<EmployeeDto> convertedList = new ArrayList<EmployeeDto>();
		for (Employee source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private EmployeeDto convert(Employee source, EmployeeDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertCommonProperties(source, prototype, context);

			prototype.setPassword(source.getPassword());
			prototype.setAccountNonExpired(source.getAccountNonExpired());
			prototype.setAccountNonLocked(source.getAccountNonLocked());
			prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
			prototype.setEnabled(source.getEnabled());
			prototype.setName(source.getName());

			if (context.getFetchProperties().contains(Employee.USER_GROUPS))
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class));

			if (context.getFetchProperties().contains(Employee.FIELDS)) {
				prototype.setFields(modelService.getDto(source.getFields(), UserFieldDto.class));
				Collections.sort(prototype.getFields(), new Comparator<UserFieldDto>() {
					@Override
					public int compare(UserFieldDto o1, UserFieldDto o2) {
						if (o1.getSort() == null)
							return o2.getSort() == null ? 0 : 1;

						if (o2.getSort() == null)
							return -1;

						return o1.getSort() - o2.getSort();
					}
				});
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
