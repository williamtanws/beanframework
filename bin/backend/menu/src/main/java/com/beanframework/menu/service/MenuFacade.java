package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.menu.domain.Menu;

public interface MenuFacade {
	
	void changePosition(UUID fromUuid, UUID toUuid, int toIndex);

	List<Menu> findMenuTree();

	List<Menu> findNavigationTreeByUserGroup(List<UUID> userGroupUuids);

}
