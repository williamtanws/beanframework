package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.menu.domain.Menu;

public interface MenuService {

	void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;

	List<Menu> findMenuTree(boolean enabled) throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
	
	List<Menu> findMenuTreeByCurrentUser() throws Exception;
}
