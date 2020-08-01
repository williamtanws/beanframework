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
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.user.domain.UserAuthority;

public class DtoUserAuthorityConverter extends AbstractDtoConverter<UserAuthority, UserAuthorityDto> implements DtoConverter<UserAuthority, UserAuthorityDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserAuthorityConverter.class);

	@Override
	public UserAuthorityDto convert(UserAuthority source, DtoConverterContext context) throws ConverterException {
		try {
			UserAuthorityDto target = new UserAuthorityDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<UserAuthorityDto> convert(List<UserAuthority> sources, DtoConverterContext context) throws ConverterException {
		List<UserAuthorityDto> convertedList = new ArrayList<UserAuthorityDto>();
		for (UserAuthority source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}
