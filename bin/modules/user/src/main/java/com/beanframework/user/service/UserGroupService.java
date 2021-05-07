package com.beanframework.user.service;

import com.beanframework.user.domain.UserGroup;

public interface UserGroupService {

	void generateUserGroupAttribute(UserGroup model) throws Exception;

	void generateUserAuthority(UserGroup model) throws Exception;

}
