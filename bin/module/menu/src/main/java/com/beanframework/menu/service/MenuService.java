package com.beanframework.menu.service;

import java.util.UUID;

import com.beanframework.user.domain.UserGroup;

public interface MenuService {
	
	void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;

	void removeUserGroupsRel(UserGroup userGroup) throws Exception;
}
