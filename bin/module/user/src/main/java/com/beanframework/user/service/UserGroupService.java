package com.beanframework.user.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public interface UserGroupService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	void removeUserGroupsRel(UserGroup userGroup) throws Exception;

	void removeUserRightsRel(UserRight userRight) throws Exception;

	void removeUserPermissionsRel(UserPermission userPermission) throws Exception;
}
