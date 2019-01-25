package com.beanframework.backoffice.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.backoffice.data.CommentDto;
import com.beanframework.comment.domain.Comment;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;

public class EntityCommentConverter implements EntityConverter<CommentDto, Comment> {

	@Autowired
	private ModelService modelService;

	@Override
	public Comment convert(CommentDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Comment.UUID, source.getUuid());
				Comment prototype = modelService.findOneEntityByProperties(properties, true, Comment.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Comment.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Comment convert(CommentDto source, Comment prototype) {

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
