package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.user.domain.UserPermission;

public class DtoUserPermissionConverter implements DtoConverter<UserPermission, UserPermissionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserPermissionConverter.class);

	@Autowired
	private ModelService modelService;

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

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setSort(source.getSort());

		try {
			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));

			prototype.setFields(modelService.getDto(source.getFields(), UserPermissionFieldDto.class));
			Collections.sort(prototype.getFields(), new Comparator<UserPermissionFieldDto>() {
				@Override
				public int compare(UserPermissionFieldDto o1, UserPermissionFieldDto o2) {
					if (o1.getSort() == null)
						return o2.getSort() == null ? 0 : 1;

					if (o2.getSort() == null)
						return -1;

					return o1.getSort() - o2.getSort();
				}
			});

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
