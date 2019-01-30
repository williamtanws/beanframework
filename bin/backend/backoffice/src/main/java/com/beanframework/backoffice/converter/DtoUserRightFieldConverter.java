package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
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
	public UserRightFieldDto convert(UserRightField source, ModelAction action) throws ConverterException {
		return convert(source, new UserRightFieldDto(), action);
	}

	public List<UserRightFieldDto> convert(List<UserRightField> sources, ModelAction action) throws ConverterException {
		List<UserRightFieldDto> convertedList = new ArrayList<UserRightFieldDto>();
		for (UserRightField source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	public UserRightFieldDto convert(UserRightField source, UserRightFieldDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());

		if (action.isInitializeCollection()) {
			try {
				prototype.setDynamicField(modelService.getDto(source.getDynamicField(), action, DynamicFieldDto.class));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw new ConverterException(e.getMessage(), e);
			}
		}

		return prototype;
	}

}
