package com.beanframework.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.user.domain.UserRight;

public interface UserRightFacade {

	Page<UserRight> page(UserRight userRight, int page, int size, Direction direction, String... properties);

}
