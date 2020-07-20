package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserGroupFieldDto;
import com.beanframework.user.domain.UserGroup;

public class DtoUserGroupConverter extends AbstractDtoConverter<UserGroup, UserGroupDto> implements DtoConverter<UserGroup, UserGroupDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserGroupConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroupDto convert(UserGroup source, DtoConverterContext context) throws ConverterException {
		return convert(source, new UserGroupDto(), context);
	}

	public List<UserGroupDto> convert(List<UserGroup> sources, DtoConverterContext context) throws ConverterException {
		List<UserGroupDto> convertedList = new ArrayList<UserGroupDto>();
		for (UserGroup source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserGroupDto convert(UserGroup source, UserGroupDto prototype, DtoConverterContext context) throws ConverterException {

		try {

			convertCommonProperties(source, prototype, context);
			prototype.setName(source.getName());

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

	private void convertAll(UserGroup source, UserGroupDto prototype, DtoConverterContext context) throws Exception {

		prototype.setUserAuthorities(modelService.getDto(source.getUserAuthorities(), UserAuthorityDto.class, context));
		prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class, context));
		prototype.setFields(modelService.getDto(source.getFields(), UserGroupFieldDto.class, context));
		if (prototype.getFields() != null)
			Collections.sort(prototype.getFields(), new Comparator<UserGroupFieldDto>() {
				@Override
				public int compare(UserGroupFieldDto o1, UserGroupFieldDto o2) {
					if (o1.getDynamicFieldSlot().getSort() == null)
						return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

					if (o2.getDynamicFieldSlot().getSort() == null)
						return -1;

					return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
				}
			});
	}

	private void convertBasic(UserGroup source, UserGroupDto prototype, DtoConverterContext context) throws Exception {
	}
}