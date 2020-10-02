package com.beanframework.comment.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.user.domain.User;

public interface CommentService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	void removeUserRel(User user) throws Exception;
}
