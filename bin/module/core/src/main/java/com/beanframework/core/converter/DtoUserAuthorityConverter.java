package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserAuthority;

public class DtoUserAuthorityConverter extends AbstractDtoConverter<UserAuthority, UserAuthorityDto> implements DtoConverter<UserAuthority, UserAuthorityDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserAuthorityConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserAuthorityDto convert(UserAuthority source, DtoConverterContext context) throws ConverterException {
		return convert(source, new UserAuthorityDto(), context);
	}

	public List<UserAuthorityDto> convert(List<UserAuthority> sources, DtoConverterContext context) throws ConverterException {
		List<UserAuthorityDto> convertedList = new ArrayList<UserAuthorityDto>();
		for (UserAuthority source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserAuthorityDto convert(UserAuthority source, UserAuthorityDto prototype, DtoConverterContext context) throws ConverterException {
		try {

			convertCommonProperties(source, prototype, context);

			if (ConvertRelationType.ALL == context.getConverModelType()) {
				convertAll(source, prototype, context);
			} else if (ConvertRelationType.BASIC == context.getConverModelType()) {
				convertRelation(source, prototype, context);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

	private void convertAll(UserAuthority source, UserAuthorityDto prototype, DtoConverterContext context) throws Exception {
		prototype.setEnabled(source.getEnabled());
		prototype.setUserPermission(modelService.getDto(source.getUserPermission(), UserPermissionDto.class, new DtoConverterContext(ConvertRelationType.ALL)));
		prototype.setUserRight(modelService.getDto(source.getUserRight(), UserRightDto.class, new DtoConverterContext(ConvertRelationType.ALL)));

	}

	private void convertRelation(UserAuthority source, UserAuthorityDto prototype, DtoConverterContext context) throws Exception {
		prototype.setEnabled(source.getEnabled());
	}

}
