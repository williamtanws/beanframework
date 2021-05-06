package com.beanframework.user.service;

import com.beanframework.user.domain.UserGroup;

public interface UserGroupService {

	void generateUserGroupField(UserGroup model) throws Exception;

	void generateUserAuthority(UserGroup model) throws Exception;

}
