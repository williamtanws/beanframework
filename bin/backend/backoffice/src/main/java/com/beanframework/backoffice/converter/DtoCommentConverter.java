package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.data.UserDto;

public class DtoCommentConverter implements DtoConverter<Comment, CommentDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCommentConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public CommentDto convert(Comment source, ModelAction action) throws ConverterException {
		return convert(source, new CommentDto(), action);
	}

	public List<CommentDto> convert(List<Comment> sources, ModelAction action) throws ConverterException {
		List<CommentDto> convertedList = new ArrayList<CommentDto>();
		for (Comment source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private CommentDto convert(Comment source, CommentDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setHtml(source.getHtml());
		prototype.setVisibled(source.getVisibled());
		if (source.getLastModifiedDate() == null) {
			prototype.setLastUpdatedDate(source.getCreatedDate());
		} else {
			prototype.setLastUpdatedDate(source.getLastModifiedDate());
		}

		try {
			ModelAction disableInitialCollectionAction = new ModelAction();
			disableInitialCollectionAction.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionAction, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionAction, AuditorDto.class));
			prototype.setUser(modelService.getDto(source.getUser(), action, UserDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
