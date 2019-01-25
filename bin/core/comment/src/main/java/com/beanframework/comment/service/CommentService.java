package com.beanframework.comment.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface CommentService {

	Comment create() throws Exception;

	Comment findOneEntityByUuid(UUID uuid) throws Exception;

	Comment findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Comment> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;
	
	Comment saveEntity(Comment model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<Comment> findEntityPage(DataTableRequest<T> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;
}
