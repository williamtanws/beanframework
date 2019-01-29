package com.beanframework.cronjob.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobService {

	Cronjob create() throws Exception;

	Cronjob findOneEntityByUuid(UUID uuid) throws Exception;

	Cronjob findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Cronjob> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	Cronjob saveEntity(Cronjob model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;
	
	List<Cronjob> findEntityStartupJobIsFalseWithQueueJob();

	<T> Page<Cronjob> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
	
}
