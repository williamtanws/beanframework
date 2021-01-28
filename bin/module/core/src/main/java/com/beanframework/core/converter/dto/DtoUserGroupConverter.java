package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.user.domain.UserGroup;

public class DtoUserGroupConverter extends AbstractDtoConverter<UserGroup, UserGroupDto> implements DtoConverter<UserGroup, UserGroupDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserGroupConverter.class);

	@Override
	public UserGroupDto convert(UserGroup source) throws ConverterException {
		return super.convert(source, new UserGroupDto());
	}
}
