package com.beanframework.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.user.domain.UserGroup;

public interface UserGroupService {

	UserGroup create();

	UserGroup initDefaults(UserGroup userGroup);

	UserGroup save(UserGroup userGroup);

	void delete(UUID uuid);

	void deleteAll();

	void deleteLanguageByLanguageUuid(UUID uuid);

	Optional<UserGroup> findEntityByUuid(UUID uuid);

	Optional<UserGroup> findEntityById(String id);

	UserGroup findByUuid(UUID uuid);

	UserGroup findById(String id);

	boolean isIdExists(String id);
	
	List<UserGroup> findByOrderByCreatedDate();

	Page<UserGroup> page(UserGroup userGroup, Pageable pageable);
}
