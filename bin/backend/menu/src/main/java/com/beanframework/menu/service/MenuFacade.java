package com.beanframework.menu.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.menu.domain.Menu;

public interface MenuFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('menu_read')";
		public static final String CREATE = "hasAuthority('menu_create')";
		public static final String UPDATE = "hasAuthority('menu_update')";
		public static final String DELETE = "hasAuthority('menu_delete')";
	}

	Menu create() throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	Menu findOneDtoByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	Menu findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	Menu createDto(Menu model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	Menu updateDto(Menu model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Menu> findDtoMenuTree() throws BusinessException;

	List<Menu> findDtoMenuTreeByCurrentUser() throws BusinessException;
}
