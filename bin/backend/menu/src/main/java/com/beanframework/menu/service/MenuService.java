package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.menu.domain.Menu;

public interface MenuService {

	void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;

	List<Menu> findEntityMenuTree() throws Exception;

	List<Menu> findEntityMenuTreeByCurrentUser(List<Menu> cachedMenuTree) throws Exception;

	List<Menu> findCachedMenuTree() throws Exception;

	void delete(UUID uuid) throws Exception;

	Menu create() throws Exception;

	Menu saveEntity(Menu menu) throws BusinessException;

	void deleteById(String id) throws BusinessException;
}
