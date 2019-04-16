package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserRightDto;

public interface UserRightFacade {

	public static interface UserRightPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "userright_read";
		public static final String AUTHORITY_CREATE = "userright_create";
		public static final String AUTHORITY_UPDATE = "userright_update";
		public static final String AUTHORITY_DELETE = "userright_delete";
		
		public static final String HAS_READ = "hasAuthority('"+AUTHORITY_READ+"')";
		public static final String HAS_CREATE = "hasAuthority('"+AUTHORITY_CREATE+"')";
		public static final String HAS_UPDATE = "hasAuthority('"+AUTHORITY_UPDATE+"')";
		public static final String HAS_DELETE = "hasAuthority('"+AUTHORITY_DELETE+"')";
	}

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_READ)
	UserRightDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_READ)
	UserRightDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_CREATE)
	UserRightDto create(UserRightDto model) throws BusinessException;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_UPDATE)
	UserRightDto update(UserRightDto model) throws BusinessException;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_READ)
	Page<UserRightDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_CREATE)
	UserRightDto createDto() throws Exception;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_READ)
	List<UserRightDto> findAllDtoUserRights() throws Exception;
}
