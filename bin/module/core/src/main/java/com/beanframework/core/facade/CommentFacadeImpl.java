package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.specification.CommentSpecification;

@Component
public class CommentFacadeImpl extends AbstractFacade<Comment, CommentDto> implements CommentFacade {

	private static final Class<Comment> entityClass = Comment.class;
	private static final Class<CommentDto> dtoClass = CommentDto.class;

	@Override
	public CommentDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public CommentDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public CommentDto create(CommentDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public CommentDto update(CommentDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<CommentDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, CommentSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass, dtoClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public CommentDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

}
