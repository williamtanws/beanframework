package com.beanframework.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.user.domain.UserRight;

public interface UserRightService {

	UserRight create();

	UserRight initDefaults(UserRight userRight);

	UserRight save(UserRight userRight);

	void delete(UUID uuid);

	void deleteAll();
	
	void deleteLanguageByLanguageUuid(UUID uuid);
	
	Optional<UserRight> findEntityByUuid(UUID uuid);
	
	Optional<UserRight> findEntityById(String id);

	UserRight findByUuid(UUID uuid);

	UserRight findById(String id);
	
	List<UserRight> findEntityAllByOrderBySortAsc();
	
	List<UserRight> findByOrderByCreatedDateDesc();

	Page<UserRight> page(UserRight userRight, Pageable pageable);


}
