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
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightFieldDto;
import com.beanframework.user.domain.UserRight;

public class DtoUserRightConverter implements DtoConverter<UserRight, UserRightDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserRightConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserRightDto convert(UserRight source, InterceptorContext context) throws ConverterException {
		return convert(source, new UserRightDto(), context);
	}

	public List<UserRightDto> convert(List<UserRight> sources, InterceptorContext context) throws ConverterException {
		List<UserRightDto> convertedList = new ArrayList<UserRightDto>();
		for (UserRight source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserRightDto convert(UserRight source, UserRightDto prototype, InterceptorContext context) throws ConverterException {

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
				prototype.setFields(modelService.getDto(source.getFields(), context, UserRightFieldDto.class));
				Collections.sort(prototype.getFields(), new Comparator<UserRightFieldDto>() {
					@Override
					public int compare(UserRightFieldDto o1, UserRightFieldDto o2) {
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
