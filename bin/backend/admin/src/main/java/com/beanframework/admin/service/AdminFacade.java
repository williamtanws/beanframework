package com.beanframework.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.admin.domain.Admin;

public interface AdminFacade {

	Page<Admin> page(Admin admin, int page, int size, Direction direction, String... properties);

	Admin getCurrentAdmin();

	Admin authenticate(String id, String password);
}
