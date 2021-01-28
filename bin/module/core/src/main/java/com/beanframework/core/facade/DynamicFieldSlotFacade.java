package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldSlotDto;

public interface DynamicFieldSlotFacade {

	DynamicFieldSlotDto findOneByUuid(UUID uuid) throws Exception;

	DynamicFieldSlotDto findOneProperties(Map<String, Object> properties) throws Exception;

	DynamicFieldSlotDto create(DynamicFieldSlotDto model) throws BusinessException;

	DynamicFieldSlotDto update(DynamicFieldSlotDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<DynamicFieldSlotDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	DynamicFieldSlotDto createDto() throws Exception;
}
