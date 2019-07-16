package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.user.domain.User;

public class DtoUserConverter extends AbstractDtoConverter<User, UserDto> implements DtoConverter<User, UserDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserConverter.class);

	@Override
	public UserDto convert(User source, DtoConverterContext context) throws ConverterException {
		return convert(source, new UserDto(), context);
	}

	public List<UserDto> convert(List<User> sources, DtoConverterContext context) throws ConverterException {
		List<UserDto> convertedList = new ArrayList<UserDto>();
		for (User source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private UserDto convert(User source, UserDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertCommonProperties(source, prototype, context);

			if (ConvertRelationType.ALL == context.getConverModelType()) {
				convertAll(source, prototype, context);
			} else if (ConvertRelationType.BASIC == context.getConverModelType()) {
				convertBasic(source, prototype, context);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

	private void convertAll(User source, UserDto prototype, DtoConverterContext context) throws Exception {
		prototype.setType(source.getType());
		prototype.setPassword(source.getPassword());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		prototype.setName(source.getName());

		prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class, new DtoConverterContext(ConvertRelationType.ALL)));

		prototype.setFields(modelService.getDto(source.getFields(), UserFieldDto.class, new DtoConverterContext(ConvertRelationType.ALL)));
		if (prototype.getFields() != null)
			Collections.sort(prototype.getFields(), new Comparator<UserFieldDto>() {
				@Override
				public int compare(UserFieldDto o1, UserFieldDto o2) {
					if (o1.getDynamicFieldSlot().getSort() == null)
						return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

					if (o2.getDynamicFieldSlot().getSort() == null)
						return -1;

					return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
				}
			});
	}

	private void convertBasic(User source, UserDto prototype, DtoConverterContext context) throws Exception {
		prototype.setType(source.getType());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		prototype.setName(source.getName());
	}

}
