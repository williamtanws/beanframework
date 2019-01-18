package com.beanframework.console.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.console.data.AdminDto;

public class DtoAdminConverter implements DtoConverter<Admin, AdminDto> {

	@Override
	public AdminDto convert(Admin source) throws ConverterException {
		return convert(source, new AdminDto());
	}

	public List<AdminDto> convert(List<Admin> sources) throws ConverterException {
		List<AdminDto> convertedList = new ArrayList<AdminDto>();
		try {
			for (Admin source : sources) {
				convertedList.add(convert(source));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private AdminDto convert(Admin source, AdminDto prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());

		return prototype;
	}

}
