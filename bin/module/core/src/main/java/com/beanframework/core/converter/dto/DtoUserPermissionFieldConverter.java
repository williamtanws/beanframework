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
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.user.domain.UserPermissionField;

public class DtoUserPermissionFieldConverter extends AbstractDtoConverter<UserPermissionField, UserPermissionFieldDto> implements DtoConverter<UserPermissionField, UserPermissionFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserPermissionFieldConverter.class);

	@Override
	public UserPermissionFieldDto convert(UserPermissionField source, DtoConverterContext context) throws ConverterException {
		try {
			UserPermissionFieldDto target = new UserPermissionFieldDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<UserPermissionFieldDto> convert(List<UserPermissionField> sources, DtoConverterContext context) throws ConverterException {
		List<UserPermissionFieldDto> convertedList = new ArrayList<UserPermissionFieldDto>();
		for (UserPermissionField source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}
}
