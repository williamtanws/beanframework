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
		public static final String AUTHORITY_READ = "menu_read";
		public static final String AUTHORITY_CREATE = "menu_create";
		public static final String AUTHORITY_UPDATE = "menu_update";
		public static final String AUTHORITY_DELETE = "menu_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	MenuDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	MenuDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_CREATE)
	MenuDto create(MenuDto model) throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_UPDATE)
	MenuDto update(MenuDto model) throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_UPDATE)
	void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	List<MenuDto> findMenuTree() throws BusinessException;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	Page<MenuDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_CREATE)
	MenuDto createDto() throws Exception;

	List<MenuDto> findMenuTreeByCurrentUser() throws Exception;

}
