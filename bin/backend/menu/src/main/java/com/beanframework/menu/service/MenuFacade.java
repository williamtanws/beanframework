package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuNavigation;

public interface MenuFacade {
	
	void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException;

	List<Menu> findDtoMenuTree() throws BusinessException;

	List<MenuNavigation> findDtoMenuNavigationByUserGroup(List<UUID> userGroupUuids) throws BusinessException;

}
