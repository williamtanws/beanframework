package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
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
	public UserGroupFieldDto convert(UserGroupField source) throws ConverterException {
		return convert(source, new UserGroupFieldDto());
	}

	public List<UserGroupFieldDto> convert(List<UserGroupField> sources) throws ConverterException {
		List<UserGroupFieldDto> convertedList = new ArrayList<UserGroupFieldDto>();
		for (UserGroupField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	public UserGroupFieldDto convert(UserGroupField source, UserGroupFieldDto prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());
		try {			
			prototype.setDynamicField(modelService.getDto(source.getDynamicField(), DynamicFieldDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
