package com.beanframework.admin.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.utils.PasswordUtils;

public class EntityAdminConverter implements EntityConverter<Admin, Admin> {

	@Autowired
	private ModelService modelService;

	@Override
	public Admin convert(Admin source) throws ConverterException {

		Admin prototype;
		try {
			prototype = modelService.create(Admin.class);
			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Admin.UUID, source.getUuid());
				Admin exists = modelService.findOneEntityByProperties(properties, Admin.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(Admin.class);
				}
			} else {
				prototype = modelService.create(Admin.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private Admin convert(Admin source, Admin prototype) {
		
		prototype.setLastModifiedDate(new Date());

		if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
			prototype.setId(source.getId());

		if (source.getAccountNonExpired() != null && source.getAccountNonExpired() != prototype.getAccountNonExpired())
			prototype.setAccountNonExpired(source.getAccountNonExpired());

		if (source.getAccountNonLocked() != null && source.getAccountNonLocked() != prototype.getAccountNonLocked())
			prototype.setAccountNonLocked(source.getAccountNonLocked());

		if (source.getCredentialsNonExpired() != null && source.getCredentialsNonExpired() != prototype.getCredentialsNonExpired())
			prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());

		if (source.getEnabled() != null && source.getEnabled() != prototype.getEnabled())
			prototype.setEnabled(source.getEnabled());

		if (StringUtils.isNotBlank(source.getPassword()))
			prototype.setPassword(PasswordUtils.encode(source.getPassword()));

		if (StringUtils.equals(source.getName(), prototype.getName()) == false)
			prototype.setName(StringUtils.strip(source.getName()));

		return prototype;
	}

}
