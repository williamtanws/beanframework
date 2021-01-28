package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.AdminDto;
import com.beanframework.user.domain.Admin;

public class DtoAdminConverter extends AbstractDtoConverter<Admin, AdminDto> implements DtoConverter<Admin, AdminDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAdminConverter.class);

	@Override
	public AdminDto convert(Admin source) throws ConverterException {
		return super.convert(source, new AdminDto());
	}
}
