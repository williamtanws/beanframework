package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.AdminCsv;

@Component
public class EntityCsvAdminConverter implements EntityCsvConverter<AdminCsv, Admin> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Admin convert(AdminCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Admin.ID, source.getId());

				Admin prototype = modelService.findOneByProperties(properties, Admin.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Admin.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Admin convertToEntity(AdminCsv source, Admin prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

			if (source.getAccountNonExpired() != null)
				prototype.setAccountNonExpired(source.getAccountNonExpired());

			if (source.getAccountNonLocked() != null)
				prototype.setAccountNonLocked(source.getAccountNonLocked());

			if (source.getCredentialsNonExpired() != null)
				prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());

			if (source.getEnabled() != null)
				prototype.setEnabled(source.getEnabled());

			if (StringUtils.isNotBlank(source.getPassword()))
				prototype.setPassword(passwordEncoder.encode(source.getPassword()));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
