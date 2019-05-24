package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.ImexDto;

public interface ImexFacade {

	public static interface ImexPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "imex_read";
		public static final String AUTHORITY_CREATE = "imex_create";
		public static final String AUTHORITY_UPDATE = "imex_update";
		public static final String AUTHORITY_DELETE = "imex_delete";
		
		public static final String HAS_READ = "hasAuthority('"+AUTHORITY_READ+"')";
		public static final String HAS_CREATE = "hasAuthority('"+AUTHORITY_CREATE+"')";
		public static final String HAS_UPDATE = "hasAuthority('"+AUTHORITY_UPDATE+"')";
		public static final String HAS_DELETE = "hasAuthority('"+AUTHORITY_DELETE+"')";
	}

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_READ)
	ImexDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_READ)
	ImexDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_CREATE)
	ImexDto create(ImexDto model) throws BusinessException;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_UPDATE)
	ImexDto update(ImexDto model) throws BusinessException;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_READ)
	Page<ImexDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_CREATE)
	ImexDto createDto() throws Exception;
}
