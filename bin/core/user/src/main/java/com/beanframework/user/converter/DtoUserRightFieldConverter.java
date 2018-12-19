package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRightField;

public class DtoUserRightFieldConverter implements DtoConverter<UserRightField, UserRightField> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DtoUserRightFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserRightField convert(UserRightField source) throws ConverterException {
		return convert(source, new UserRightField());
	}

	public List<UserRightField> convert(List<UserRightField> sources) throws ConverterException {
		List<UserRightField> convertedList = new ArrayList<UserRightField>();
		for (UserRightField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	public UserRightField convert(UserRightField source, UserRightField prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setLabel(source.getLabel());
		prototype.setValue(source.getValue());
		try {
			prototype.setLanguage(modelService.getDto(source.getLanguage()));
			prototype.setDynamicField(modelService.getDto(source.getDynamicField()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
