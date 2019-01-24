package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

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

	Page<UserGroupDto> findPage(DataTableRequest<UserGroupDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	List<UserGroupDto> findAllDtoUserGroups() throws Exception;

}
