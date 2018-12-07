package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.user.domain.UserGroup;

public interface UserGroupFacade {

	UserGroup create();

	UserGroup initDefaults(UserGroup userGroup);

	UserGroup save(UserGroup userGroup, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	UserGroup findByUuid(UUID uuid);

	UserGroup findById(String id);
	
	List<UserGroup> findByOrderByCreatedDate();

	Page<UserGroup> page(UserGroup userGroup, int page, int size, Direction direction, String... properties);

}
