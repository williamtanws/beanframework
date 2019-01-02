package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class DtoUserRightConverter implements DtoConverter<UserRight, UserRight> {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserRightConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserRight convert(UserRight source) throws ConverterException {
		return convert(source, new UserRight());
	}

	public List<UserRight> convert(List<UserRight> sources) throws ConverterException {
		List<UserRight> convertedList = new ArrayList<UserRight>();
		for (UserRight source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserRight convert(UserRight source, UserRight prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setSort(source.getSort());
		try {
			Hibernate.initialize(source.getFields());
			
			prototype.setFields(modelService.getDto(source.getFields(), UserRightField.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
