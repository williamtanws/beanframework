package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.AdminDto;

public interface AdminFacade {

	public static interface AdminPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "admin_read";
		public static final String AUTHORITY_CREATE = "admin_create";
		public static final String AUTHORITY_UPDATE = "admin_update";
		public static final String AUTHORITY_DELETE = "admin_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "'')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	AdminDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	AdminDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_CREATE)
	AdminDto create(AdminDto model) throws BusinessException;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_UPDATE)
	AdminDto update(AdminDto model) throws BusinessException;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	Page<AdminDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_CREATE)
	AdminDto createDto() throws Exception;
}
