package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CommentDto;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

public class EntityCommentConverter implements EntityConverter<CommentDto, Comment> {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private UserService userService;

	@Override
	public Comment convert(CommentDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Comment.UUID, source.getUuid());
				Comment prototype = modelService.findOneByProperties(properties, Comment.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Comment.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Comment convertToEntity(CommentDto source, Comment prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getHtml()), prototype.getHtml()) == Boolean.FALSE) {
				prototype.setHtml(StringUtils.stripToNull(source.getHtml()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getVisibled() == source.getVisibled() == Boolean.FALSE) {
				prototype.setVisibled(source.getVisibled());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getUser() == null && source.getUser() == null) {
				prototype.setUser(userService.getCurrentUser().getUuid());
				prototype.setLastModifiedDate(lastModifiedDate);
			} else if (prototype.getUser().equals(source.getUser().getUuid()) == Boolean.FALSE) {
				User entityUser = modelService.findOneByUuid(source.getUser().getUuid(), User.class);
				
				if(entityUser != null) {
					prototype.setUser(entityUser.getUuid());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
				else {
					throw new ConverterException("User UUID not found: " + source.getUser().getUuid());
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
