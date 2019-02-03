package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.UserRightFieldDto;
import com.beanframework.user.domain.UserRightField;

public class DtoUserRightFieldConverter implements DtoConverter<UserRightField, UserRightFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserRightFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserRightFieldDto convert(UserRightField source, InterceptorContext context) throws ConverterException {
		return convert(source, new UserRightFieldDto(), context);
	}

	public List<UserRightFieldDto> convert(List<UserRightField> sources, InterceptorContext context) throws ConverterException {
		List<UserRightFieldDto> convertedList = new ArrayList<UserRightFieldDto>();
		for (UserRightField source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	public UserRightFieldDto convert(UserRightField source, UserRightFieldDto prototype, InterceptorContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());

		try {

			InterceptorContext disableInitialCollectionContext = new InterceptorContext();
			disableInitialCollectionContext.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionContext, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionContext, AuditorDto.class));

			if (context.isInitializeCollection()) {

				prototype.setDynamicField(modelService.getDto(source.getDynamicField(), context, DynamicFieldDto.class));

			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}