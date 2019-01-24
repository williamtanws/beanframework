package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.UserPermissionDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface UserPermissionFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('userpermission_read')";
		public static final String CREATE = "hasAuthority('userpermission_create')";
		public static final String UPDATE = "hasAuthority('userpermission_update')";
		public static final String DELETE = "hasAuthority('userpermission_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserPermissionDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserPermissionDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	UserPermissionDto create(UserPermissionDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	UserPermissionDto update(UserPermissionDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<UserPermissionDto> findPage(DataTableRequest<UserPermissionDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	List<UserPermissionDto> findAllDtoUserPermissions() throws Exception;

}
