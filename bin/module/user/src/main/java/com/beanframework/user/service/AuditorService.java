package com.beanframework.user.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.User;

public interface AuditorService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	Auditor saveEntityByUser(User model) throws BusinessException;
}
