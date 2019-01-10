package com.beanframework.employee.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.utils.PasswordUtils;

@Component
public class EntityEmployeeProfileConverter implements EntityConverter<Employee, Employee> {

	@Autowired
	private ModelService modelService;

	@Override
	public Employee convert(Employee source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Employee.UUID, source.getUuid());
				Employee prototype = modelService.findOneEntityByProperties(properties, Employee.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Employee.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

	}

	private Employee convert(Employee source, Employee prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getAccountNonExpired() != null && source.getAccountNonExpired() != prototype.getAccountNonExpired()) {
				prototype.setAccountNonExpired(source.getAccountNonExpired());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getAccountNonLocked() != null && source.getAccountNonLocked() != prototype.getAccountNonLocked()) {
				prototype.setAccountNonLocked(source.getAccountNonLocked());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getCredentialsNonExpired() != null && source.getCredentialsNonExpired() != prototype.getCredentialsNonExpired()) {
				prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getEnabled() != null && source.getEnabled() != prototype.getEnabled()) {
				prototype.setEnabled(source.getEnabled());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.isNotBlank(source.getPassword())) {
				prototype.setPassword(PasswordUtils.encode(source.getPassword()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
