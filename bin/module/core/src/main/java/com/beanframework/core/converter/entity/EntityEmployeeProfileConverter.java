package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.user.domain.Employee;

@Component
public class EntityEmployeeProfileConverter implements EntityConverter<EmployeeDto, Employee> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Employee convert(EmployeeDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Employee.UUID, source.getUuid());
				Employee prototype = modelService.findOneByProperties(properties, Employee.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Employee.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private Employee convertToEntity(EmployeeDto source, Employee prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.isNotBlank(source.getPassword())) {
				prototype.setPassword(passwordEncoder.encode(source.getPassword()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
