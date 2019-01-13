package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserPermission;

public interface UserPermissionFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('userpermission_read')";
		public static final String CREATE = "hasAuthority('userpermission_create')";
		public static final String UPDATE = "hasAuthority('userpermission_update')";
		public static final String DELETE = "hasAuthority('userpermission_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<UserPermission> findPage(Specification<UserPermission> specification, PageRequest pageRequest) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserPermission findOneDtoByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserPermission findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	UserPermission createDto(UserPermission model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	UserPermission updateDto(UserPermission model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

}
