package com.beanframework.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.user.domain.UserPermission;

public interface UserPermissionService {

	UserPermission create();

	UserPermission initDefaults(UserPermission userPermission);

	UserPermission save(UserPermission userPermission);

	void delete(UUID uuid);

	void deleteAll();

	void deleteLanguageByLanguageUuid(UUID uuid);

	Optional<UserPermission> findEntityByUuid(UUID uuid);

	Optional<UserPermission> findEntityById(String id);

	UserPermission findByUuid(UUID uuid);

	UserPermission findById(String id);

	boolean isIdExists(String id);

	List<UserPermission> findEntityAllByOrderBySortAsc();

	List<UserPermission> findByOrderByCreatedDateDesc();

	Page<UserPermission> page(UserPermission userPermission, Pageable pageable);
}
