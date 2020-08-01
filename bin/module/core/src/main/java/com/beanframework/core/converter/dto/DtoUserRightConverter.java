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
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserRight;

public class DtoUserRightConverter extends AbstractDtoConverter<UserRight, UserRightDto> implements DtoConverter<UserRight, UserRightDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserRightConverter.class);

	@Override
	public UserRightDto convert(UserRight source, DtoConverterContext context) throws ConverterException {
		try {
			UserRightDto target = new UserRightDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<UserRightDto> convert(List<UserRight> sources, DtoConverterContext context) throws ConverterException {
		List<UserRightDto> convertedList = new ArrayList<UserRightDto>();
		for (UserRight source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}
}
