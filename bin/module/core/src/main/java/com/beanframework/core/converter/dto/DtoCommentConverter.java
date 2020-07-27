package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AdminDto;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.data.UserDto;

public class DtoCommentConverter extends AbstractDtoConverter<Comment, CommentDto> implements DtoConverter<Comment, CommentDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCommentConverter.class);

	@Override
	public CommentDto convert(Comment source, DtoConverterContext context) throws ConverterException {
		try {
			AdminDto target = new AdminDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<CommentDto> convert(List<Comment> sources, DtoConverterContext context) throws ConverterException {
		List<CommentDto> convertedList = new ArrayList<CommentDto>();
		for (Comment source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	public CommentDto convert(Comment source, CommentDto prototype, DtoConverterContext context) throws ConverterException {
		try {
			convertCommonProperties(source, prototype, context);

			prototype.setHtml(source.getHtml());
			prototype.setVisibled(source.getVisibled());
			prototype.setUser(modelService.getDto(source.getUser(), UserDto.class, new DtoConverterContext(ConvertRelationType.BASIC)));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
