package com.beanframework.admin.service;

import com.beanframework.admin.domain.Admin;

public interface AdminService {

	Admin findDtoAuthenticate(String id, String password) throws Exception;

}
