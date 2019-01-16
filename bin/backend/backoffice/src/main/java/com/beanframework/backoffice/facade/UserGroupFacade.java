package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.backoffice.data.UserGroupSearch;
import com.beanframework.common.exception.BusinessException;

public interface UserGroupFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('usergroup_read')";
		public static final String CREATE = "hasAuthority('usergroup_create')";
		public static final String UPDATE = "hasAuthority('usergroup_update')";
		public static final String DELETE = "hasAuthority('usergroup_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<UserGroupDto> findPage(UserGroupSearch search, PageRequest pageRequest) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserGroupDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserGroupDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	UserGroupDto create(UserGroupDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	UserGroupDto update(UserGroupDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<UserGroupDto> findAllDtoUserGroups() throws Exception;
}
