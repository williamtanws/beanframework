package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MediaDto;

public interface MediaFacade {

	public static interface MediaPreAuthorizeEnum {
		public static final String READ = "hasAuthority('media_read')";
		public static final String CREATE = "hasAuthority('media_create')";
		public static final String UPDATE = "hasAuthority('media_update')";
		public static final String DELETE = "hasAuthority('media_delete')";
	}

	@PreAuthorize(MediaPreAuthorizeEnum.READ)
	MediaDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.READ)
	MediaDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.CREATE)
	MediaDto create(MediaDto model) throws BusinessException;

	@PreAuthorize(MediaPreAuthorizeEnum.UPDATE)
	MediaDto update(MediaDto model) throws BusinessException;

	@PreAuthorize(MediaPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(MediaPreAuthorizeEnum.READ)
	Page<MediaDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.CREATE)
	MediaDto createDto() throws Exception;
}
