package com.beanframework.admin.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.admin.domain.Admin;

public interface AdminFacade {

	Admin save(Admin admin, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	Admin findByUuid(UUID uuid);

	Admin findById(String id);

	Page<Admin> page(Admin admin, int page, int size, Direction direction, String... properties);

	Admin getCurrentAdmin();

	Admin authenticate(String id, String password);
}
