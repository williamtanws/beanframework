package com.beanframework.admin.service;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.exception.BusinessException;

public interface AdminService {

	Admin create() throws Exception;

	Admin saveEntity(Admin model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

	Admin findAuthenticate(String id, String password) throws Exception;

	Admin getCurrentUser() throws Exception;

}
