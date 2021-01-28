package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MenuDto;

public interface MenuFacade {

	MenuDto findOneByUuid(UUID uuid) throws Exception;

	MenuDto findOneProperties(Map<String, Object> properties) throws Exception;

	MenuDto create(MenuDto model) throws BusinessException;

	MenuDto update(MenuDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException;

	List<MenuDto> findMenuTree() throws BusinessException;

	Page<MenuDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	MenuDto createDto() throws Exception;

	List<MenuDto> findMenuTreeByCurrentUser() throws Exception;

}
