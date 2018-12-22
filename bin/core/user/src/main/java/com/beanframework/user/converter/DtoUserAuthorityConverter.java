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
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public class DtoUserAuthorityConverter implements DtoConverter<UserAuthority, UserAuthority> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DtoUserAuthorityConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserAuthority convert(UserAuthority source) throws ConverterException {
		return convert(source, new UserAuthority());
	}

	public List<UserAuthority> convert(List<UserAuthority> sources) throws ConverterException {
		List<UserAuthority> convertedList = new ArrayList<UserAuthority>();
		for (UserAuthority source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserAuthority convert(UserAuthority source, UserAuthority prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setEnabled(source.getEnabled());
		try {
			Hibernate.initialize(source.getUserPermission());
			Hibernate.initialize(source.getUserRight());
			
			prototype.setUserPermission(modelService.getDto(source.getUserPermission(), UserPermission.class));
			prototype.setUserRight(modelService.getDto(source.getUserRight(), UserRight.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
