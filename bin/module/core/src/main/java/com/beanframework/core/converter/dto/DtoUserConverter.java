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
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.User;

public class DtoUserConverter extends AbstractDtoConverter<User, UserDto> implements DtoConverter<User, UserDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserConverter.class);

	@Override
	public UserDto convert(User source, DtoConverterContext context) throws ConverterException {
		try {
			UserDto target = new UserDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<UserDto> convert(List<User> sources, DtoConverterContext context) throws ConverterException {
		List<UserDto> convertedList = new ArrayList<UserDto>();
		for (User source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}
