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
		public static final String READ = "hasAuthority('userpermission_read')";
		public static final String CREATE = "hasAuthority('userpermission_create')";
		public static final String UPDATE = "hasAuthority('userpermission_update')";
		public static final String DELETE = "hasAuthority('userpermission_delete')";
	}

	@PreAuthorize(UserPermissionPreAuthorizeEnum.READ)
	UserPermissionDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.READ)
	UserPermissionDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.CREATE)
	UserPermissionDto create(UserPermissionDto model) throws BusinessException;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.UPDATE)
	UserPermissionDto update(UserPermissionDto model) throws BusinessException;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.READ)
	Page<UserPermissionDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserPermissionPreAuthorizeEnum.CREATE)
	UserPermissionDto createDto() throws Exception;

	List<UserPermissionDto> findAllDtoUserPermissions() throws Exception;

}
