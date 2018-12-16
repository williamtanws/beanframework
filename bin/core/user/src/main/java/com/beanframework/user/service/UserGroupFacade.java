package com.beanframework.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.user.domain.UserGroup;

public interface UserGroupFacade {

	Page<UserGroup> page(UserGroup userGroup, int page, int size, Direction direction, String... properties);

}
