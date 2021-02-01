package com.beanframework.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private ModelService modelService;

	@Override
	public void removeUserRel(User model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Comment.USER, model.getUuid());
		List<Comment> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Comment.class);

		if (entities != null)
			for (Comment comment : entities) {
				modelService.deleteEntityByLegacyModel(comment, Comment.class);
			}
	}
}
