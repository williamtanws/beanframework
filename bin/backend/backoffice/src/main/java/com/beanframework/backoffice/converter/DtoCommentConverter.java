package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.backoffice.data.CommentDto;
import com.beanframework.backoffice.data.UserDto;
import com.beanframework.comment.domain.Comment;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;

public class DtoCommentConverter implements DtoConverter<Comment, CommentDto> {

	@Autowired
	private ModelService modelService;

	@Override
	public CommentDto convert(Comment source) throws ConverterException {
		return convert(source, new CommentDto());
	}

	public List<CommentDto> convert(List<Comment> sources) throws ConverterException {
		List<CommentDto> convertedList = new ArrayList<CommentDto>();
		for (Comment source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private CommentDto convert(Comment source, CommentDto prototype) throws ConverterException {

		try {
			prototype.setUuid(source.getUuid());
			prototype.setId(source.getId());
			prototype.setCreatedBy(source.getCreatedBy());
			prototype.setCreatedDate(source.getCreatedDate());
			prototype.setLastModifiedBy(source.getLastModifiedBy());
			prototype.setLastModifiedDate(source.getLastModifiedDate());

			prototype.setHtml(source.getHtml());
			prototype.setVisibled(source.getVisibled());
			if (source.getLastModifiedDate() == null) {
				prototype.setLastUpdatedDate(source.getCreatedDate());
			} else {
				prototype.setLastUpdatedDate(source.getLastModifiedDate());
			}
			prototype.setUser(modelService.getDto(source.getUser(), UserDto.class));
			
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
