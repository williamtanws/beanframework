package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.menu.domain.Menu;

public interface MenuService {

	void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;

	List<Menu> findMenuTree() throws Exception;

	List<Menu> findNavigationTreeByUserGroup(List<UUID> userGroupUuids) throws Exception;
}
