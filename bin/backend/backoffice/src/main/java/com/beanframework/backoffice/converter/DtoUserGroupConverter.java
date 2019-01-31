package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.data.AuditorDto;
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
	public UserGroupDto convert(UserGroup source, ModelAction action) throws ConverterException {
		return convert(source, new UserGroupDto(), action);
	}

	public List<UserGroupDto> convert(List<UserGroup> sources, ModelAction action) throws ConverterException {
		List<UserGroupDto> convertedList = new ArrayList<UserGroupDto>();
		for (UserGroup source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private UserGroupDto convert(UserGroup source, UserGroupDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());

		try {
			ModelAction disableInitialCollectionAction = new ModelAction();
			disableInitialCollectionAction.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionAction, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionAction, AuditorDto.class));

			if (action.isInitializeCollection()) {
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), action, UserGroupDto.class));
				prototype.setUserAuthorities(modelService.getDto(source.getUserAuthorities(), action, UserAuthorityDto.class));
				prototype.setFields(modelService.getDto(source.getFields(), action, UserGroupFieldDto.class));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}
}
