package com.beanframework.admin.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.admin.domain.Admin;

public interface AdminService {

	Admin create();

	Admin initDefaults(Admin admin);

	Admin save(Admin admin);

	void delete(UUID uuid);

	void deleteAll();
	
	Optional<Admin> findEntityByUuid(UUID uuid);

	Optional<Admin> findEntityById(String id);

	Admin findByUuid(UUID uuid);

	Admin findById(String id);

	Page<Admin> page(Admin admin, Pageable pageable);

	Admin authenticate(String id, String password);

}
