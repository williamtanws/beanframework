package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.InterceptorContext;
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
	public UserGroupDto convert(UserGroup source, InterceptorContext context) throws ConverterException {
		return convert(source, new UserGroupDto(), context);
	}

	public List<UserGroupDto> convert(List<UserGroup> sources, InterceptorContext context) throws ConverterException {
		List<UserGroupDto> convertedList = new ArrayList<UserGroupDto>();
		for (UserGroup source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserGroupDto convert(UserGroup source, UserGroupDto prototype, InterceptorContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());

		try {
			InterceptorContext disableInitialCollectionContext = new InterceptorContext();
			disableInitialCollectionContext.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionContext, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionContext, AuditorDto.class));

			if (context.isInitializeCollection()) {
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), context, UserGroupDto.class));
				prototype.setUserAuthorities(modelService.getDto(source.getUserAuthorities(), context, UserAuthorityDto.class));
				prototype.setFields(modelService.getDto(source.getFields(), context, UserGroupFieldDto.class));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}
}
