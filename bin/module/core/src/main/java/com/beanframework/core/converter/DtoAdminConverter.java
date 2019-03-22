package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.AdminDto;

public class DtoAdminConverter extends AbstractDtoConverter<Admin, AdminDto> implements DtoConverter<Admin, AdminDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAdminConverter.class);

	@Override
	public AdminDto convert(Admin source, DtoConverterContext context) throws ConverterException {
		return convert(source, new AdminDto(), context);
	}

	public List<AdminDto> convert(List<Admin> sources, DtoConverterContext context) throws ConverterException {
		List<AdminDto> convertedList = new ArrayList<AdminDto>();
		try {
			for (Admin source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private AdminDto convert(Admin source, AdminDto prototype, DtoConverterContext context) throws ConverterException {
		try {
			convertCommonProperties(source, prototype, context);

			prototype.setAccountNonExpired(source.getAccountNonExpired());
			prototype.setAccountNonLocked(source.getAccountNonLocked());
			prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
			prototype.setEnabled(source.getEnabled());
			prototype.setName(source.getName());

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
