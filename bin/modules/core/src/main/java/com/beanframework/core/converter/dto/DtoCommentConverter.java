package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.CommentDto;

public class DtoCommentConverter extends AbstractDtoConverter<Comment, CommentDto> implements DtoConverter<Comment, CommentDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCommentConverter.class);

	@Override
	public CommentDto convert(Comment source) throws ConverterException {
		return super.convert(source, new CommentDto());
	}

}
