package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;
import com.beanframework.user.converter.DtoUserGroupConverter;

public class DtoUserConverter implements DtoConverter<User, User> {
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DtoUserGroupConverter dtoUserGroupConverter;

	@Override
	public User convert(User source) {
		return convert(source, modelService.create(User.class));
	}

	public List<User> convert(List<User> sources) {
		List<User> convertedList = new ArrayList<User>();
		for (User source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private User convert(User source, User prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setAccountNonExpired(source.isAccountNonExpired());
		prototype.setAccountNonLocked(source.isAccountNonLocked());
		prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
		prototype.setEnabled(source.isEnabled());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		
		Hibernate.initialize(source.getUserGroups());
		prototype.setUserGroups(dtoUserGroupConverter.convert(source.getUserGroups()));

		return prototype;
	}

}
