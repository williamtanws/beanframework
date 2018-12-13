package com.beanframework.admin.converter;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.utils.PasswordUtils;

public class EntityAdminConverter implements EntityConverter<Admin, Admin> {

	@Autowired
	private ModelService modelService;

	@Override
	public Admin convert(Admin source) {

		Admin prototype = null;
		if (source.getUuid() != null) {
			prototype = modelService.findByUuid(source.getUuid(), Admin.class);
			if (prototype == null) {
				prototype = modelService.create(Admin.class);
			}
		}
		else {
			prototype = modelService.create(Admin.class);
		}

		return convert(source, prototype);
	}

	private Admin convert(Admin source, Admin prototype) {

		prototype.setId(source.getId());
		prototype.setAccountNonExpired(source.isAccountNonExpired());
		prototype.setAccountNonLocked(source.isAccountNonLocked());
		prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
		prototype.setEnabled(source.isEnabled());
		prototype.setLastModifiedDate(new Date());
		
		if (StringUtils.isNotEmpty(source.getPassword())) {
			prototype.setPassword(PasswordUtils.encode(source.getPassword()));
		}

		return prototype;
	}

}
