package com.beanframework.admin.converter;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminService;
import com.beanframework.user.utils.PasswordUtils;

@Component
public class EntityAdminConverter implements Converter<Admin, Admin> {

	@Autowired
	private AdminService adminService;

	@Override
	public Admin convert(Admin source) {

		Optional<Admin> prototype = null;
		if (source.getUuid() != null) {
			prototype = adminService.findEntityByUuid(source.getUuid());
			if (prototype.isPresent() == false) {
				prototype = Optional.of(adminService.create());
			}
		}
		else {
			prototype = Optional.of(adminService.create());
		}

		return convert(source, prototype.get());
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
