package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.user.domain.UserPermission;

public class DtoUserPermissionConverter extends AbstractDtoConverter<UserPermission, UserPermissionDto> implements DtoConverter<UserPermission, UserPermissionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserPermissionConverter.class);

	@Override
	public UserPermissionDto convert(UserPermission source, DtoConverterContext context) throws ConverterException {
		return convert(source, new UserPermissionDto(), context);
	}

	public List<UserPermissionDto> convert(List<UserPermission> sources, DtoConverterContext context) throws ConverterException {
		List<UserPermissionDto> convertedList = new ArrayList<UserPermissionDto>();
		for (UserPermission source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserPermissionDto convert(UserPermission source, UserPermissionDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertCommonProperties(source, prototype, context);

			if (ConvertRelationType.ALL == context.getConverModelType()) {
				convertAll(source, prototype, context);
			} else if (ConvertRelationType.BASIC == context.getConverModelType()) {
				convertBasic(source, prototype, context);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

	private void convertAll(UserPermission source, UserPermissionDto prototype, DtoConverterContext context) throws Exception {
		prototype.setName(source.getName());
		prototype.setSort(source.getSort());

		prototype.setFields(modelService.getDto(source.getFields(), UserPermissionFieldDto.class, new DtoConverterContext(ConvertRelationType.ALL)));
		if (prototype.getFields() != null)
			Collections.sort(prototype.getFields(), new Comparator<UserPermissionFieldDto>() {
				@Override
				public int compare(UserPermissionFieldDto o1, UserPermissionFieldDto o2) {
					if (o1.getDynamicFieldSlot().getSort() == null)
						return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

					if (o2.getDynamicFieldSlot().getSort() == null)
						return -1;

					return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
				}
			});

	}

	private void convertBasic(UserPermission source, UserPermissionDto prototype, DtoConverterContext context) throws Exception {
		prototype.setName(source.getName());
		prototype.setSort(source.getSort());
	}

}
