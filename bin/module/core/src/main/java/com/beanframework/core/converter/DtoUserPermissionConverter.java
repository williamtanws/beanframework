package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.InterceptorContext;
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
	public UserPermissionDto convert(UserPermission source, InterceptorContext context) throws ConverterException {
		return convert(source, new UserPermissionDto(), context);
	}

	public List<UserPermissionDto> convert(List<UserPermission> sources, InterceptorContext context) throws ConverterException {
		List<UserPermissionDto> convertedList = new ArrayList<UserPermissionDto>();
		for (UserPermission source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserPermissionDto convert(UserPermission source, UserPermissionDto prototype, InterceptorContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setSort(source.getSort());

		try {
			InterceptorContext disableInitialCollectionContext = new InterceptorContext();
			disableInitialCollectionContext.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionContext, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionContext, AuditorDto.class));

			if (context.isInitializeCollection()) {
				prototype.setFields(modelService.getDto(source.getFields(), context, UserPermissionFieldDto.class));
				Collections.sort(prototype.getFields(), new Comparator<UserPermissionFieldDto>() {
					@Override
					public int compare(UserPermissionFieldDto o1, UserPermissionFieldDto o2) {
						if (o1.getDynamicField() == null || o1.getDynamicField().getSort() == null)
							return (o2.getDynamicField() == null || o2.getDynamicField().getSort() == null) ? 0 : 1;

						if (o2.getDynamicField() == null || o2.getDynamicField().getSort() == null)
							return -1;

						return o1.getDynamicField().getSort() - o2.getDynamicField().getSort();
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
