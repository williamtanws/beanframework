package com.beanframework.admin.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;

public class DtoAdminConverter implements DtoConverter<Admin, Admin> {

	@Override
	public Admin convert(Admin source) throws ConverterException {
		return convert(source, new Admin());
	}

	public List<Admin> convert(List<Admin> sources) throws ConverterException {
		List<Admin> convertedList = new ArrayList<Admin>();
		try {
			for (Admin source : sources) {
				convertedList.add(convert(source));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), this);
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
