package com.beanframework.user.service;

import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public interface UserGroupService {

	void removeUserGroupsRel(UserGroup userGroup) throws Exception;

	void removeUserRightsRel(UserRight userRight) throws Exception;

	void removeUserPermissionsRel(UserPermission userPermission) throws Exception;
}
