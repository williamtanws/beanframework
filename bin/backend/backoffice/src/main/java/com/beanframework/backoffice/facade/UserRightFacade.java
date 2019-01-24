package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.UserRightDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface UserRightFacade {

	public static interface UserRightPreAuthorizeEnum {
		public static final String READ = "hasAuthority('userright_read')";
		public static final String CREATE = "hasAuthority('userright_create')";
		public static final String UPDATE = "hasAuthority('userright_update')";
		public static final String DELETE = "hasAuthority('userright_delete')";
	}

	@PreAuthorize(UserRightPreAuthorizeEnum.READ)
	UserRightDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(UserRightPreAuthorizeEnum.READ)
	UserRightDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(UserRightPreAuthorizeEnum.CREATE)
	UserRightDto create(UserRightDto model) throws BusinessException;

	@PreAuthorize(UserRightPreAuthorizeEnum.UPDATE)
	UserRightDto update(UserRightDto model) throws BusinessException;

	@PreAuthorize(UserRightPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<UserRightDto> findPage(DataTableRequest<UserRightDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	List<UserRightDto> findAllDtoUserRights() throws Exception;

}
