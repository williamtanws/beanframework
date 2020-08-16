package com.beanframework.user.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.user.domain.Admin;

public interface AdminService {

	Admin findAuthenticate(String id, String password) throws Exception;

	Admin getCurrentUser() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

}
