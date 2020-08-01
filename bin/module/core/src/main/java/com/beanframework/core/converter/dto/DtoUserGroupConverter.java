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
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.user.domain.UserGroup;

public class DtoUserGroupConverter extends AbstractDtoConverter<UserGroup, UserGroupDto> implements DtoConverter<UserGroup, UserGroupDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserGroupConverter.class);

	@Override
	public UserGroupDto convert(UserGroup source, DtoConverterContext context) throws ConverterException {
		try {
			UserGroupDto target = new UserGroupDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<UserGroupDto> convert(List<UserGroup> sources, DtoConverterContext context) throws ConverterException {
		List<UserGroupDto> convertedList = new ArrayList<UserGroupDto>();
		for (UserGroup source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}
}
