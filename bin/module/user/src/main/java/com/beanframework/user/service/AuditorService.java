package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.User;

public interface AuditorService {

	Auditor create() throws Exception;

	Auditor findOneEntityByUuid(UUID uuid) throws Exception;

	Auditor findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Auditor> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	Auditor saveEntity(User model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<Auditor> findEntityPage(DataTableRequest<T> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;
}
