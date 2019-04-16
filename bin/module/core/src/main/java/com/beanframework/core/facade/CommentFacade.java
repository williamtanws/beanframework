package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CommentDto;

public interface CommentFacade {

	public static interface CommentPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "comment_read";
		public static final String AUTHORITY_CREATE = "comment_create";
		public static final String AUTHORITY_UPDATE = "comment_update";
		public static final String AUTHORITY_DELETE = "comment_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	CommentDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	CommentDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_CREATE)
	CommentDto create(CommentDto model) throws BusinessException;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_UPDATE)
	CommentDto update(CommentDto model) throws BusinessException;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	Page<CommentDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	List<CommentDto> findAllDtoComments() throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_CREATE)
	CommentDto createDto() throws Exception;

}
