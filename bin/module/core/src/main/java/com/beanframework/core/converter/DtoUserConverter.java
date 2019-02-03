package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.user.domain.User;

public class DtoUserConverter implements DtoConverter<User, UserDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserDto convert(User source, InterceptorContext context) throws ConverterException {
		return convert(source, new UserDto(), context);
	}

	public List<UserDto> convert(List<User> sources, InterceptorContext context) throws ConverterException {
		List<UserDto> convertedList = new ArrayList<UserDto>();
		for (User source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserDto convert(User source, UserDto prototype, InterceptorContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setPassword(source.getPassword());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		prototype.setName(source.getName());

		try {
			InterceptorContext disableInitialCollectionContext = new InterceptorContext();
			disableInitialCollectionContext.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionContext, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionContext, AuditorDto.class));

			if (context.isInitializeCollection()) {
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), context, UserGroupDto.class));
				prototype.setFields(modelService.getDto(source.getFields(), context, UserFieldDto.class));
				Collections.sort(prototype.getFields(), new Comparator<UserFieldDto>() {
					@Override
					public int compare(UserFieldDto o1, UserFieldDto o2) {
						if (o1.getDynamicField() == null || o1.getDynamicField().getSort() == null)
							return (o2.getDynamicField() == null || o2.getDynamicField().getSort() == null) ? 0 : 1;

						if (o2.getDynamicField() == null || o2.getDynamicField().getSort() == null)
							return -1;

						return o1.getDynamicField().getSort() - o2.getDynamicField().getSort();
					}
				});
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
