package com.beanframework.admin.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.admin.domain.Admin;

public interface AdminFacade {

	Admin save(Admin admin);

	void delete(UUID uuid);

	void deleteAll();

	Admin findByUuid(UUID uuid);

	Admin findById(String id);

	Page<Admin> page(Admin admin, int page, int size, Direction direction, String... properties);

	Admin getCurrentAdmin();

	Admin authenticate(String id, String password);
}
