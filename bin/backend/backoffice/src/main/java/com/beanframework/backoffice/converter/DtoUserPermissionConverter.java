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
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.user.domain.UserPermission;

public class DtoUserPermissionConverter implements DtoConverter<UserPermission, UserPermissionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserPermissionConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermissionDto convert(UserPermission source) throws ConverterException {
		return convert(source, new UserPermissionDto());
	}

	public List<UserPermissionDto> convert(List<UserPermission> sources) throws ConverterException {
		List<UserPermissionDto> convertedList = new ArrayList<UserPermissionDto>();
		for (UserPermission source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserPermissionDto convert(UserPermission source, UserPermissionDto prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setSort(source.getSort());
		try {
			if (Hibernate.isInitialized(source.getFields()))
				prototype.setFields(modelService.getDto(source.getFields(), UserPermissionFieldDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
