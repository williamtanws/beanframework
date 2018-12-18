package com.beanframework.admin.service;

import com.beanframework.admin.domain.Admin;

public interface AdminFacade {

	Admin getCurrentAdmin();

	Admin authenticate(String id, String password) throws Exception;
}
