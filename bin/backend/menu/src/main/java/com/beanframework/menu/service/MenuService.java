package com.beanframework.menu.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.menu.domain.Menu;

public interface MenuService {

	Menu create() throws Exception;

	Menu findOneEntityByUuid(UUID uuid) throws Exception;

	Menu findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	Menu saveEntity(Menu model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws Exception;
	
	void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;

	List<Menu> findEntityMenuTree() throws Exception;

	List<Menu> filterEntityMenuTreeByCurrentUser(List<Menu> cachedMenuTree) throws Exception;
}
