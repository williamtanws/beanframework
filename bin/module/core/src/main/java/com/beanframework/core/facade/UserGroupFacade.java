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
		public static final String READ = "hasAuthority('usergroup_read')";
		public static final String CREATE = "hasAuthority('usergroup_create')";
		public static final String UPDATE = "hasAuthority('usergroup_update')";
		public static final String DELETE = "hasAuthority('usergroup_delete')";
	}

	@PreAuthorize(UserGroupPreAuthorizeEnum.READ)
	UserGroupDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.READ)
	UserGroupDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.CREATE)
	UserGroupDto create(UserGroupDto model) throws BusinessException;

	@PreAuthorize(UserGroupPreAuthorizeEnum.UPDATE)
	UserGroupDto update(UserGroupDto model) throws BusinessException;

	@PreAuthorize(UserGroupPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(UserGroupPreAuthorizeEnum.READ)
	Page<UserGroupDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserGroupPreAuthorizeEnum.CREATE)
	UserGroupDto createDto() throws Exception;

	List<UserGroupDto> findAllDtoUserGroups() throws Exception;

}
