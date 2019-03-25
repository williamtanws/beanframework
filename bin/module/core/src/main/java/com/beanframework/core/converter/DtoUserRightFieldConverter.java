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
import com.beanframework.core.data.UserRightFieldDto;
import com.beanframework.user.domain.UserRightField;

public class DtoUserRightFieldConverter extends AbstractDtoConverter<UserRightField, UserRightFieldDto> implements DtoConverter<UserRightField, UserRightFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserRightFieldConverter.class);

	@Override
	public UserRightFieldDto convert(UserRightField source, DtoConverterContext context) throws ConverterException {
		return convert(source, new UserRightFieldDto(), context);
	}

	public List<UserRightFieldDto> convert(List<UserRightField> sources, DtoConverterContext context) throws ConverterException {
		List<UserRightFieldDto> convertedList = new ArrayList<UserRightFieldDto>();
		for (UserRightField source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	public UserRightFieldDto convert(UserRightField source, UserRightFieldDto prototype, DtoConverterContext context) throws ConverterException {

		try {

			convertCommonProperties(source, prototype, context);

			prototype.setValue(source.getValue());
			prototype.setSort(source.getSort());

			if (context.isFetchable(UserRightField.class, UserRightField.DYNAMIC_FIELD))
				prototype.setDynamicField(modelService.getDto(source.getDynamicField(), DynamicFieldDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
