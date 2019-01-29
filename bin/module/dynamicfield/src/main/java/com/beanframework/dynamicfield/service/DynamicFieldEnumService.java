package com.beanframework.dynamicfield.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public interface DynamicFieldEnumService {

	DynamicFieldEnum create() throws Exception;

	DynamicFieldEnum findOneEntityByUuid(UUID uuid) throws Exception;

	DynamicFieldEnum findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<DynamicFieldEnum> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	DynamicFieldEnum saveEntity(DynamicFieldEnum model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<DynamicFieldEnum> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
