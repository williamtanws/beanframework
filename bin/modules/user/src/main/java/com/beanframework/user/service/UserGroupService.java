package com.beanframework.user.service;

import com.beanframework.user.domain.UserGroup;

public interface UserGroupService {

	void generateUserGroupFieldsOnInitialDefault(UserGroup model) throws Exception;

	void generateUserGroupFieldOnLoad(UserGroup model) throws Exception;

}
