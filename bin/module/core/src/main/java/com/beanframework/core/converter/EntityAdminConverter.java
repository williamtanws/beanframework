package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AdminDto;

public class EntityAdminConverter implements EntityConverter<AdminDto, Admin> {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Admin convert(AdminDto source, EntityConverterContext context) throws ConverterException {

		try {
			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Admin.UUID, source.getUuid());
				Admin prototype = modelService.findOneEntityByProperties(properties, true, Admin.class);

				if (prototype != null) {
					return convertDto(source, prototype);
				}
			}
			return convertDto(source, modelService.create(Admin.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Admin convertDto(AdminDto source, Admin prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getAccountNonExpired() == null) {
			if (prototype.getAccountNonExpired() != null) {
				prototype.setAccountNonExpired(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getAccountNonExpired() == null || prototype.getAccountNonExpired().equals(source.getAccountNonExpired()) == false) {
				prototype.setAccountNonExpired(source.getAccountNonExpired());
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (source.getAccountNonLocked() == null) {
			if (prototype.getAccountNonLocked() != null) {
				prototype.setAccountNonLocked(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getAccountNonLocked() == null || prototype.getAccountNonLocked().equals(source.getAccountNonLocked()) == false) {
				prototype.setAccountNonLocked(source.getAccountNonLocked());
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (source.getCredentialsNonExpired() == null) {
			if (prototype.getCredentialsNonExpired() != null) {
				prototype.setCredentialsNonExpired(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getCredentialsNonExpired() == null || prototype.getCredentialsNonExpired().equals(source.getCredentialsNonExpired()) == false) {
				prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (source.getEnabled() == null) {
			if (prototype.getEnabled() != null) {
				prototype.setEnabled(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getEnabled() == null || prototype.getEnabled().equals(source.getEnabled()) == false) {
				prototype.setEnabled(source.getEnabled());
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (StringUtils.isNotBlank(source.getPassword())) {
			prototype.setPassword(passwordEncoder.encode(source.getPassword()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
