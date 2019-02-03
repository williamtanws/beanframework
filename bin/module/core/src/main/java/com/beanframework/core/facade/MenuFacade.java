package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MenuDto;

public interface MenuFacade {

	public static interface MenuPreAuthorizeEnum {
		public static final String READ = "hasAuthority('menu_read')";
		public static final String CREATE = "hasAuthority('menu_create')";
		public static final String UPDATE = "hasAuthority('menu_update')";
		public static final String DELETE = "hasAuthority('menu_delete')";
	}

	@PreAuthorize(MenuPreAuthorizeEnum.READ)
	MenuDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.READ)
	MenuDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.CREATE)
	MenuDto create(MenuDto model) throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.UPDATE)
	MenuDto update(MenuDto model) throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.UPDATE)
	void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.READ)
	List<MenuDto> findMenuTree() throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.READ)
	Page<MenuDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.CREATE)
	MenuDto createDto() throws Exception;

}
