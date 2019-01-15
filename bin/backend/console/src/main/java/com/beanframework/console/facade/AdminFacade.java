package com.beanframework.console.facade;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

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
	Page<AdminDto> findPage(Specification<AdminDto> findByCriteria, PageRequest of) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	AdminDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	AdminDto create(AdminDto adminCreate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	AdminDto update(AdminDto adminUpdate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;
}
