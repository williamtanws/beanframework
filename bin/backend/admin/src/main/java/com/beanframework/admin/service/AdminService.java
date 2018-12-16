package com.beanframework.admin.service;

import com.beanframework.admin.domain.Admin;

public interface AdminService {

	Admin authenticate(String id, String password);

}
