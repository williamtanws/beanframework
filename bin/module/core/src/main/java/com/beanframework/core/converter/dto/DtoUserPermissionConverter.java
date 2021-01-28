package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.user.domain.UserPermission;

public class DtoUserPermissionConverter extends AbstractDtoConverter<UserPermission, UserPermissionDto> implements DtoConverter<UserPermission, UserPermissionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserPermissionConverter.class);

	@Override
	public UserPermissionDto convert(UserPermission source) throws ConverterException {
		return super.convert(source, new UserPermissionDto());
	}

}
