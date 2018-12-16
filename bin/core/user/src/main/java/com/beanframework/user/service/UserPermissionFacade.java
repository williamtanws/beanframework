package com.beanframework.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.user.domain.UserPermission;

public interface UserPermissionFacade {

	Page<UserPermission> page(UserPermission userPermission, int page, int size, Direction direction,
			String... properties);

}
