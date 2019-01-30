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
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightFieldDto;
import com.beanframework.user.domain.UserRight;

public class DtoUserRightConverter implements DtoConverter<UserRight, UserRightDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserRightConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserRightDto convert(UserRight source, ModelAction action) throws ConverterException {
		return convert(source, new UserRightDto(), action);
	}

	public List<UserRightDto> convert(List<UserRight> sources, ModelAction action) throws ConverterException {
		List<UserRightDto> convertedList = new ArrayList<UserRightDto>();
		for (UserRight source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private UserRightDto convert(UserRight source, UserRightDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setSort(source.getSort());

		if (action.isInitializeCollection()) {
			try {
				prototype.setFields(modelService.getDto(source.getFields(), action, UserRightFieldDto.class));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw new ConverterException(e.getMessage(), e);
			}
		}

		return prototype;
	}

}
