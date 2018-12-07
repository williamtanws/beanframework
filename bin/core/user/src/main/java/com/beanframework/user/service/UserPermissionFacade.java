package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.user.domain.UserPermission;

public interface UserPermissionFacade {

	UserPermission create();

	UserPermission initDefaults(UserPermission userPermission);

	UserPermission save(UserPermission userPermission, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	UserPermission findByUuid(UUID uuid);

	UserPermission findById(String id);
	
	List<UserPermission> findByOrderByCreatedDate();

	Page<UserPermission> page(UserPermission userPermission, int page, int size, Direction direction, String... properties);

}
