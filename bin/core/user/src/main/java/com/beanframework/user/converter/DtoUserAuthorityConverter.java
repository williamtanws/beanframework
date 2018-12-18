package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.user.domain.UserAuthority;

public class DtoUserAuthorityConverter implements DtoConverter<UserAuthority, UserAuthority> {

	@Autowired
	private DtoUserPermissionConverter dtoUserPermissionConverter;

	@Autowired
	private DtoUserRightConverter dtoUserRightConverter;

	@Override
	public UserAuthority convert(UserAuthority source) {
		return convert(source, new UserAuthority());
	}

	public List<UserAuthority> convert(List<UserAuthority> sources) {
		List<UserAuthority> convertedList = new ArrayList<UserAuthority>();
		for (UserAuthority source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserAuthority convert(UserAuthority source, UserAuthority prototype) {

		prototype.setUuid(source.getUuid());

		prototype.setEnabled(source.getEnabled());
//		Hibernate.initialize(source.getUserPermission());
		prototype.setUserPermission(dtoUserPermissionConverter.convert(source.getUserPermission()));
//		Hibernate.initialize(source.getUserRight());
		prototype.setUserRight(dtoUserRightConverter.convert(source.getUserRight()));

		return prototype;
	}

}
