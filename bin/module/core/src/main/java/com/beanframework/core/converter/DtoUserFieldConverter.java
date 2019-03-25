package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserField;

public class DtoUserFieldConverter extends AbstractDtoConverter<UserField, UserFieldDto> implements DtoConverter<UserField, UserFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserFieldConverter.class);

	@Override
	public UserFieldDto convert(UserField source, DtoConverterContext context) throws ConverterException {
		return convert(source, new UserFieldDto(), context);
	}

	public List<UserFieldDto> convert(List<UserField> sources, DtoConverterContext context) throws ConverterException {
		List<UserFieldDto> convertedList = new ArrayList<UserFieldDto>();
		for (UserField source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	public UserFieldDto convert(UserField source, UserFieldDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertCommonProperties(source, prototype, context);

			prototype.setValue(source.getValue());
			prototype.setSort(source.getSort());

			if (context.isFetchable(User.class, UserField.DYNAMIC_FIELD))
				prototype.setDynamicField(modelService.getDto(source.getDynamicField(), DynamicFieldDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
