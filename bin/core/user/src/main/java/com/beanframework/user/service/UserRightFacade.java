package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.user.domain.UserRight;

public interface UserRightFacade {

	UserRight create();

	UserRight initDefaults(UserRight userRight);

	UserRight save(UserRight userRight, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	UserRight findByUuid(UUID uuid);

	UserRight findById(String id);
	
	List<UserRight> findByOrderByCreatedDate();
	
	Page<UserRight> page(UserRight userRight, int page, int size, Direction direction, String... properties);

}
