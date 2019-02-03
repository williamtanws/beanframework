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
		public static final String READ = "hasAuthority('admin_read')";
		public static final String CREATE = "hasAuthority('admin_create')";
		public static final String UPDATE = "hasAuthority('admin_update')";
		public static final String DELETE = "hasAuthority('admin_delete')";
	}

	@PreAuthorize(AdminPreAuthorizeEnum.READ)
	AdminDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.READ)
	AdminDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.CREATE)
	AdminDto create(AdminDto model) throws BusinessException;

	@PreAuthorize(AdminPreAuthorizeEnum.UPDATE)
	AdminDto update(AdminDto model) throws BusinessException;

	@PreAuthorize(AdminPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(AdminPreAuthorizeEnum.READ)
	Page<AdminDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(AdminPreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;
}
