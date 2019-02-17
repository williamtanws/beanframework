package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.UserGroupFieldDto;
import com.beanframework.user.domain.UserGroupField;

public class DtoUserGroupFieldConverter implements DtoConverter<UserGroupField, UserGroupFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserGroupFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroupFieldDto convert(UserGroupField source, DtoConverterContext context) throws ConverterException {
		return convert(source, new UserGroupFieldDto(), context);
	}

	public List<UserGroupFieldDto> convert(List<UserGroupField> sources, DtoConverterContext context) throws ConverterException {
		List<UserGroupFieldDto> convertedList = new ArrayList<UserGroupFieldDto>();
		for (UserGroupField source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	public UserGroupFieldDto convert(UserGroupField source, UserGroupFieldDto prototype, DtoConverterContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());
		prototype.setSort(source.getSort());

		try {
			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));

			prototype.setDynamicField(modelService.getDto(source.getDynamicField(), DynamicFieldDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
