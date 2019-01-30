package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserAuthority;

public class DtoUserAuthorityConverter implements DtoConverter<UserAuthority, UserAuthorityDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserAuthorityConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserAuthorityDto convert(UserAuthority source, ModelAction action) throws ConverterException {
		return convert(source, new UserAuthorityDto(), action);
	}

	public List<UserAuthorityDto> convert(List<UserAuthority> sources, ModelAction action) throws ConverterException {
		List<UserAuthorityDto> convertedList = new ArrayList<UserAuthorityDto>();
		for (UserAuthority source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private UserAuthorityDto convert(UserAuthority source, UserAuthorityDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setEnabled(source.getEnabled());

		if (action.isInitializeCollection()) {
			try {
				prototype.setUserPermission(modelService.getDto(source.getUserPermission(), action, UserPermissionDto.class));
				prototype.setUserRight(modelService.getDto(source.getUserRight(), action, UserRightDto.class));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw new ConverterException(e.getMessage(), e);
			}
		}

		return prototype;
	}

}
