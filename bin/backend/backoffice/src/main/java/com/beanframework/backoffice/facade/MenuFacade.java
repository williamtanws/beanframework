package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.MenuDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.menu.domain.Menu;

public interface MenuFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('menu_read')";
		public static final String CREATE = "hasAuthority('menu_create')";
		public static final String UPDATE = "hasAuthority('menu_update')";
		public static final String DELETE = "hasAuthority('menu_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	MenuDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	MenuDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	MenuDto create(MenuDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	MenuDto update(MenuDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<MenuDto> findMenuTree() throws BusinessException;

	List<Menu> findMenuTreeByCurrentUser() throws Exception;

	Page<MenuDto> findPage(DataTableRequest<MenuDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

}
