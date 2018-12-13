package com.beanframework.admin.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;

public class DtoAdminConverter implements DtoConverter<Admin, Admin> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public Admin convert(Admin source) {
		return convert(source, modelService.create(Admin.class));
	}

	public List<Admin> convert(List<Admin> sources) {
		List<Admin> convertedList = new ArrayList<Admin>();
		for (Admin source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Admin convert(Admin source, Admin prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setAccountNonExpired(source.isAccountNonExpired());
		prototype.setAccountNonLocked(source.isAccountNonLocked());
		prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
		prototype.setEnabled(source.isEnabled());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		return prototype;
	}

}
