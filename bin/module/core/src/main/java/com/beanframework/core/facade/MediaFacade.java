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
		public static final String AUTHORITY_READ = "media_read";
		public static final String AUTHORITY_CREATE = "media_create";
		public static final String AUTHORITY_UPDATE = "media_update";
		public static final String AUTHORITY_DELETE = "media_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_READ)
	MediaDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_READ)
	MediaDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_CREATE)
	MediaDto create(MediaDto model) throws BusinessException;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_UPDATE)
	MediaDto update(MediaDto model) throws BusinessException;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_READ)
	Page<MediaDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_CREATE)
	MediaDto createDto() throws Exception;

}
