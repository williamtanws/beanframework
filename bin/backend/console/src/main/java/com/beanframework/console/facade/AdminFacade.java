package com.beanframework.console.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.console.data.AdminDto;

public interface AdminFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('admin_read')";
		public static final String CREATE = "hasAuthority('admin_create')";
		public static final String UPDATE = "hasAuthority('admin_update')";
		public static final String DELETE = "hasAuthority('admin_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	AdminDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	AdminDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	AdminDto create(AdminDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	AdminDto update(AdminDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	List<AdminDto> findAllDtoAdmins() throws Exception;

	Page<AdminDto> findPage(DataTableRequest<AdminDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;
}
