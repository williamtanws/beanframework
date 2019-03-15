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
		public static final String READ = "hasAuthority('comment_read')";
		public static final String CREATE = "hasAuthority('comment_create')";
		public static final String UPDATE = "hasAuthority('comment_update')";
		public static final String DELETE = "hasAuthority('comment_delete')";
	}

	@PreAuthorize(CommentPreAuthorizeEnum.READ)
	CommentDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.READ)
	CommentDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.CREATE)
	CommentDto create(CommentDto model) throws BusinessException;

	@PreAuthorize(CommentPreAuthorizeEnum.UPDATE)
	CommentDto update(CommentDto model) throws BusinessException;

	@PreAuthorize(CommentPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(CommentPreAuthorizeEnum.READ)
	Page<CommentDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.READ)
	List<CommentDto> findAllDtoComments() throws Exception;

	@PreAuthorize(CommentPreAuthorizeEnum.CREATE)
	CommentDto createDto() throws Exception;

}
