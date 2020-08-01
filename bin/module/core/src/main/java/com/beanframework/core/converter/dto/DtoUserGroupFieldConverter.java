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
import com.beanframework.core.data.UserGroupFieldDto;
import com.beanframework.user.domain.UserGroupField;

public class DtoUserGroupFieldConverter extends AbstractDtoConverter<UserGroupField, UserGroupFieldDto> implements DtoConverter<UserGroupField, UserGroupFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserGroupFieldConverter.class);

	@Override
	public UserGroupFieldDto convert(UserGroupField source, DtoConverterContext context) throws ConverterException {
		try {
			UserGroupFieldDto target = new UserGroupFieldDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<UserGroupFieldDto> convert(List<UserGroupField> sources, DtoConverterContext context) throws ConverterException {
		List<UserGroupFieldDto> convertedList = new ArrayList<UserGroupFieldDto>();
		for (UserGroupField source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}
