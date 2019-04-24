package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserGroupDto;

public interface UserGroupFacade {

	public static interface UserGroupPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "usergroup_read";
		public static final String AUTHORITY_CREATE = "usergroup_create";
		public static final String AUTHORITY_UPDATE = "usergroup_update";
		public static final String AUTHORITY_DELETE = "usergroup_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_READ)
	UserGroupDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_READ)
	UserGroupDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_CREATE)
	UserGroupDto create(UserGroupDto model) throws BusinessException;

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_UPDATE)
	UserGroupDto update(UserGroupDto model) throws BusinessException;

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_READ)
	Page<UserGroupDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.HAS_CREATE)
	UserGroupDto createDto() throws Exception;
}
