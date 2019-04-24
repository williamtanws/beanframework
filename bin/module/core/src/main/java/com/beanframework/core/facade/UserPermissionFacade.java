package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserPermissionDto;

public interface UserPermissionFacade {

	public static interface UserPermissionPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "userpermission_read";
		public static final String AUTHORITY_CREATE = "userpermission_create";
		public static final String AUTHORITY_UPDATE = "userpermission_update";
		public static final String AUTHORITY_DELETE = "userpermission_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
	UserPermissionDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
	UserPermissionDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_CREATE)
	UserPermissionDto create(UserPermissionDto model) throws BusinessException;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_UPDATE)
	UserPermissionDto update(UserPermissionDto model) throws BusinessException;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
	Page<UserPermissionDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_CREATE)
	UserPermissionDto createDto() throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
	List<UserPermissionDto> findAllDtoUserPermissions() throws Exception;

}
