package com.beanframework.dynamicfield.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public interface DynamicFieldSlotService {

	DynamicFieldSlot create() throws Exception;

	DynamicFieldSlot findOneEntityByUuid(UUID uuid) throws Exception;

	DynamicFieldSlot findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<DynamicFieldSlot> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	DynamicFieldSlot saveEntity(DynamicFieldSlot model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<DynamicFieldSlot> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
