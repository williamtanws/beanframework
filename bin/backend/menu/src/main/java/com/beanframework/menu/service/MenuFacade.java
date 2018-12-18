package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.menu.domain.Menu;

public interface MenuFacade {
	
	void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException;

	List<Menu> findMenuTree() throws BusinessException;

	List<Menu> findNavigationTreeByUserGroup(List<UUID> userGroupUuids) throws BusinessException;

}
