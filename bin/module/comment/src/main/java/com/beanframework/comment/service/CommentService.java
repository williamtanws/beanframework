package com.beanframework.comment.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface CommentService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
