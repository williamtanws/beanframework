package com.beanframework.user.service;

import com.beanframework.user.domain.UserPermission;

public interface UserPermissionService {

	void generateUserPermissionFieldsOnInitialDefault(UserPermission model) throws Exception;

	void generateUserPermissionFieldOnLoad(UserPermission model) throws Exception;

}
