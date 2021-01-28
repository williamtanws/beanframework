package com.beanframework.comment.service;

import com.beanframework.user.domain.User;

public interface CommentService {

	void removeUserRel(User user) throws Exception;
}
