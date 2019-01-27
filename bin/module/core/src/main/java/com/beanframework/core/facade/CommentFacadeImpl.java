package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CommentDto;
import com.beanframework.comment.domain.Comment;
import com.beanframework.comment.service.CommentService;

@Component
public class CommentFacadeImpl implements CommentFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private CommentService commentService;

	@Override
	public CommentDto findOneByUuid(UUID uuid) throws Exception {
		Comment entity = commentService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, CommentDto.class);
	}

	@Override
	public CommentDto findOneProperties(Map<String, Object> properties) throws Exception {
		Comment entity = commentService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, CommentDto.class);
	}

	@Override
	public CommentDto create(CommentDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public CommentDto update(CommentDto model) throws BusinessException {
		return save(model);
	}

	public CommentDto save(CommentDto dto) throws BusinessException {
		try {
			Comment entity = modelService.getEntity(dto, Comment.class);
			entity = (Comment) commentService.saveEntity(entity);

			return modelService.getDto(entity, CommentDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		commentService.deleteByUuid(uuid);
	}
	
	@Override
	public Page<CommentDto> findPage(DataTableRequest<CommentDto> dataTableRequest) throws Exception {
		Page<Comment> page = commentService.findEntityPage(dataTableRequest);
		List<CommentDto> dtos = modelService.getDto(page.getContent(), CommentDto.class);
		return new PageImpl<CommentDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return commentService.count();
	}
	
	@Override
	public List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {

		List<Object[]> revisions = commentService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Comment)
				entityObject[0] = modelService.getDto(entityObject[0], CommentDto.class);
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {
		return commentService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<CommentDto> findAllDtoComments() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Comment.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(commentService.findEntityBySorts(sorts, false), CommentDto.class);
	}

}
