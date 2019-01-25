package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.backoffice.data.UserDto;
import com.beanframework.backoffice.data.UserFieldDto;
import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;

public class DtoUserConverter implements DtoConverter<User, UserDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserDto convert(User source) throws ConverterException {
		return convert(source, new UserDto());
	}

	public List<UserDto> convert(List<User> sources) throws ConverterException {
		List<UserDto> convertedList = new ArrayList<UserDto>();
		for (User source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserDto convert(User source, UserDto prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setPassword(source.getPassword());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		prototype.setName(source.getName());
		try {
			prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class));
			prototype.setFields(modelService.getDto(source.getFields(), UserFieldDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
