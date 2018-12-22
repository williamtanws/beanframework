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
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

public class DtoUserPermissionConverter implements DtoConverter<UserPermission, UserPermission> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DtoUserPermissionConverter.class);
	
	@Autowired
	private ModelService modelService;

	@Override
	public UserPermission convert(UserPermission source) throws ConverterException {		
		return convert(source, new UserPermission());
	}

	public List<UserPermission> convert(List<UserPermission> sources) throws ConverterException {
		List<UserPermission> convertedList = new ArrayList<UserPermission>();
		for (UserPermission source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserPermission convert(UserPermission source, UserPermission prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setSort(source.getSort());
		try {
			Hibernate.initialize(source.getUserPermissionFields());
			
			prototype.setUserPermissionFields(modelService.getDto(source.getUserPermissionFields(), UserPermissionField.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
