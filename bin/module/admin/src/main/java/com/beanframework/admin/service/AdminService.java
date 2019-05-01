package com.beanframework.admin.service;

import java.util.List;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.data.DataTableRequest;

public interface AdminService {

	Admin findAuthenticate(String id, String password) throws Exception;

	Admin getCurrentUser() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

}
