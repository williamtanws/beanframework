package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.AdminCsv;

@Component
public class EntityCsvAdminConverter implements EntityConverter<AdminCsv, Admin> {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Admin convert(AdminCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Admin.ID, source.getId());

				Admin prototype = modelService.findOneEntityByProperties(properties, Admin.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, new Admin());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public Admin convert(AdminCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private Admin convert(AdminCsv source, Admin prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setAccountNonExpired(source.isAccountNonExpired());
			prototype.setAccountNonLocked(source.isAccountNonLocked());
			prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
			prototype.setEnabled(source.isEnabled());
			if (StringUtils.isNotBlank(source.getPassword()))
				prototype.setPassword(passwordEncoder.encode(source.getPassword()));
			prototype.setName(source.getName());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
