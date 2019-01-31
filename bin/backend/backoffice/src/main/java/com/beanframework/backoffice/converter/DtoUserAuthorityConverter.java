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
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserAuthority;

public class DtoUserAuthorityConverter implements DtoConverter<UserAuthority, UserAuthorityDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserAuthorityConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserAuthorityDto convert(UserAuthority source, InterceptorContext context) throws ConverterException {
		return convert(source, new UserAuthorityDto(), context);
	}

	public List<UserAuthorityDto> convert(List<UserAuthority> sources, InterceptorContext context) throws ConverterException {
		List<UserAuthorityDto> convertedList = new ArrayList<UserAuthorityDto>();
		for (UserAuthority source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserAuthorityDto convert(UserAuthority source, UserAuthorityDto prototype, InterceptorContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setEnabled(source.getEnabled());

		try {
			InterceptorContext disableInitialCollectionContext = new InterceptorContext();
			disableInitialCollectionContext.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionContext, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionContext, AuditorDto.class));

			if (context.isInitializeCollection()) {
				prototype.setUserPermission(modelService.getDto(source.getUserPermission(), context, UserPermissionDto.class));
				prototype.setUserRight(modelService.getDto(source.getUserRight(), context, UserRightDto.class));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
