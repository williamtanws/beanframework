package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CommentDto;
import com.beanframework.comment.domain.Comment;

public class EntityCommentConverter implements EntityConverter<CommentDto, Comment> {

	@Autowired
	private ModelService modelService;

	@Override
	public Comment convert(CommentDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Comment.UUID, source.getUuid());
				Comment prototype = modelService.findOneEntityByProperties(properties, true, Comment.class);

				if (prototype != null) {
					return convertDto(source, prototype);
				}
			}

			return convertDto(source, modelService.create(Comment.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Comment convertDto(CommentDto source, Comment prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getHtml()), prototype.getHtml()) == false) {
			prototype.setHtml(StringUtils.stripToNull(source.getHtml()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (prototype.getVisibled() == source.getVisibled() == false) {
			prototype.setVisibled(source.getVisibled());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
