package com.beanframework.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.admin.domain.Admin;

public interface AdminService {

	Page<Admin> page(Admin admin, Pageable pageable);

	Admin authenticate(String id, String password);

}
