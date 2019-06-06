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

import com.beanframework.comment.domain.Comment;
import com.beanframework.comment.service.CommentService;
import com.beanframework.comment.specification.CommentSpecification;
import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CommentDto;

@Component
public class CommentFacadeImpl implements CommentFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CommentService commentService;

	@Override
	public CommentDto findOneByUuid(UUID uuid) throws Exception {
		Comment entity = modelService.findOneByUuid(uuid, Comment.class);

		return modelService.getDto(entity, CommentDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public CommentDto findOneProperties(Map<String, Object> properties) throws Exception {
		Comment entity = modelService.findOneByProperties(properties, Comment.class);

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
			entity = modelService.saveEntity(entity, Comment.class);

			return modelService.getDto(entity, CommentDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Comment.class);
	}

	@Override
	public Page<CommentDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Comment> page = modelService.findPage(CommentSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Comment.class);

		List<CommentDto> dtos = modelService.getDto(page.getContent(), CommentDto.class, new DtoConverterContext(ConvertRelationType.BASIC));
		return new PageImpl<CommentDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Comment.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = commentService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Comment) {

				entityObject[0] = modelService.getDto(entityObject[0], CommentDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return commentService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<CommentDto> findAllDtoComments() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Comment.CREATED_DATE, Sort.Direction.DESC);

		List<Comment> comments = modelService.findByPropertiesBySortByResult(null, sorts, null, null, Comment.class);
		return modelService.getDto(comments, CommentDto.class);
	}

	@Override
	public CommentDto createDto() throws Exception {
		Comment comment = modelService.create(Comment.class);
		return modelService.getDto(comment, CommentDto.class);
	}

}
