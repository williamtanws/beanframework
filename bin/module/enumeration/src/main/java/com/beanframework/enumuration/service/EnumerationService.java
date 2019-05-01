package com.beanframework.enumuration.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.enumuration.domain.Enumeration;

public interface EnumerationService {

	Enumeration findOneEntityByUuid(UUID uuid) throws Exception;

	Enumeration findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Enumeration> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	Enumeration saveEntity(Enumeration model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	Page<Enumeration> findEntityPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
