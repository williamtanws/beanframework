package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightFieldDto;
import com.beanframework.user.domain.UserRight;

public class DtoUserRightConverter extends AbstractDtoConverter<UserRight, UserRightDto> implements DtoConverter<UserRight, UserRightDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserRightConverter.class);

	@Override
	public UserRightDto convert(UserRight source, DtoConverterContext context) throws ConverterException {
		return convert(source, new UserRightDto(), context);
	}

	public List<UserRightDto> convert(List<UserRight> sources, DtoConverterContext context) throws ConverterException {
		List<UserRightDto> convertedList = new ArrayList<UserRightDto>();
		for (UserRight source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserRightDto convert(UserRight source, UserRightDto prototype, DtoConverterContext context) throws ConverterException {

		try {

			convertCommonProperties(source, prototype, context);

			prototype.setName(source.getName());
			prototype.setSort(source.getSort());

			if (context.isFetchable(UserRight.class, UserRight.FIELDS)) {
				prototype.setFields(modelService.getDto(source.getFields(), UserRightFieldDto.class));
				Collections.sort(prototype.getFields(), new Comparator<UserRightFieldDto>() {
					@Override
					public int compare(UserRightFieldDto o1, UserRightFieldDto o2) {
						if (o1.getSort() == null)
							return o2.getSort() == null ? 0 : 1;

						if (o2.getSort() == null)
							return -1;

						return o1.getSort() - o2.getSort();
					}
				});
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}
}
