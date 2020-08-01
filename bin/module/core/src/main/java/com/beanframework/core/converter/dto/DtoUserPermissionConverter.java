package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.user.domain.UserPermission;

public class DtoUserPermissionConverter extends AbstractDtoConverter<UserPermission, UserPermissionDto> implements DtoConverter<UserPermission, UserPermissionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserPermissionConverter.class);

	@Override
	public UserPermissionDto convert(UserPermission source, DtoConverterContext context) throws ConverterException {
		try {
			UserPermissionDto target = new UserPermissionDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<UserPermissionDto> convert(List<UserPermission> sources, DtoConverterContext context) throws ConverterException {
		List<UserPermissionDto> convertedList = new ArrayList<UserPermissionDto>();
		for (UserPermission source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}
