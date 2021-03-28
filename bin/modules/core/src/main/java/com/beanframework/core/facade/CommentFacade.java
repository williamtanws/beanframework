package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CommentDto;

public interface CommentFacade {

	CommentDto findOneByUuid(UUID uuid) throws Exception;

	CommentDto findOneProperties(Map<String, Object> properties) throws Exception;

	CommentDto create(CommentDto model) throws BusinessException;

	CommentDto update(CommentDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<CommentDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	CommentDto createDto() throws Exception;

}
