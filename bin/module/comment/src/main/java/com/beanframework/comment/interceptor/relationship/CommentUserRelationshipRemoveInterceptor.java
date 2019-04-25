package com.beanframework.comment.interceptor.relationship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;

public class CommentUserRelationshipRemoveInterceptor extends AbstractRemoveInterceptor<User> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onRemove(User model, InterceptorContext context) throws InterceptorException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Comment.USER + "." + User.UUID, model.getUuid());
			List<Comment> entities = modelService.findEntityByPropertiesAndSorts(properties, null, null, null, Comment.class);

			for (Comment comment : entities) {
				modelService.deleteByEntity(comment, Comment.class);
			}

		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}

	}
}
