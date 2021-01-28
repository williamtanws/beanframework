package com.beanframework.user.service;

import com.beanframework.user.domain.Admin;

public interface AdminService {

	Admin findAuthenticate(String id, String password) throws Exception;

	Admin getCurrentUser() throws Exception;
}
