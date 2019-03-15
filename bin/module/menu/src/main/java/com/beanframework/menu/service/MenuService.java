package com.beanframework.menu.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.menu.domain.Menu;

public interface MenuService {

	Menu create() throws Exception;

	Menu findOneEntityByUuid(UUID uuid) throws Exception;

	Menu findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	Menu saveEntity(Menu model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws Exception;

	void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;

	List<Menu> findEntityMenuTree(boolean enabled) throws Exception;

	<T> Page<Menu> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
