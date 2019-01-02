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
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;

public class DtoUserGroupConverter implements DtoConverter<UserGroup, UserGroup> {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserGroupConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroup convert(UserGroup source) throws ConverterException {
		return convert(source, new UserGroup());
	}

	public List<UserGroup> convert(List<UserGroup> sources) throws ConverterException {
		List<UserGroup> convertedList = new ArrayList<UserGroup>();
		for (UserGroup source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserGroup convert(UserGroup source, UserGroup prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		try {
			Hibernate.initialize(source.getUserGroups());
			Hibernate.initialize(source.getUserAuthorities());

			prototype.setUserGroups(source.getUserGroups());
			prototype.setUserAuthorities(modelService.getDto(source.getUserAuthorities(), UserAuthority.class));
			prototype.setFields(modelService.getDto(source.getFields(), UserGroupField.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}
}
