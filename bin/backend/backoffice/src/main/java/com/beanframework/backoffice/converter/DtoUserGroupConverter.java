package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserGroupFieldDto;
import com.beanframework.user.domain.UserGroup;

public class DtoUserGroupConverter implements DtoConverter<UserGroup, UserGroupDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserGroupConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroupDto convert(UserGroup source) throws ConverterException {
		return convert(source, new UserGroupDto());
	}

	public List<UserGroupDto> convert(List<UserGroup> sources) throws ConverterException {
		List<UserGroupDto> convertedList = new ArrayList<UserGroupDto>();
		for (UserGroup source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserGroupDto convert(UserGroup source, UserGroupDto prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());

		try {
			if (Hibernate.isInitialized(source.getUserGroups()))
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class));

			if (Hibernate.isInitialized(source.getUserAuthorities()))
				prototype.setUserAuthorities(modelService.getDto(source.getUserAuthorities(), UserAuthorityDto.class));

			if (Hibernate.isInitialized(source.getFields()))
				prototype.setFields(modelService.getDto(source.getFields(), UserGroupFieldDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}
}
