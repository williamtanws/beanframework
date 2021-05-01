package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.menu.domain.Menu;

public interface MenuService {
	
	void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;
	
	void generateMenuFieldsOnInitialDefault(Menu model) throws Exception;

	void generateMenuFieldOnLoad(Menu model) throws Exception;

	List<Menu> findMenuBreadcrumbsByPath(String path) throws Exception;
}
