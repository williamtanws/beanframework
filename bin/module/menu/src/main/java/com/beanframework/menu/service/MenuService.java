package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.UserGroup;

public interface MenuService {
	
	public static final String CACHE_MENU_TREE = "MenuTree";

	void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;

	@Cacheable(value = CACHE_MENU_TREE, key = "#enabled")
	List<Menu> findMenuTree(boolean enabled) throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	List<Menu> filterMenuByUserGroups(List<Menu> entities, List<UserGroup> userGroups) throws Exception;

	void removeUserGroupsRel(UserGroup userGroup) throws Exception;
}
