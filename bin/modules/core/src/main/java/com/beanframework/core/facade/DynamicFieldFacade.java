package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldDto;

public interface DynamicFieldFacade {

	DynamicFieldDto findOneByUuid(UUID uuid) throws Exception;

	DynamicFieldDto findOneProperties(Map<String, Object> properties) throws Exception;

	DynamicFieldDto create(DynamicFieldDto model) throws BusinessException;

	DynamicFieldDto update(DynamicFieldDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<DynamicFieldDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	DynamicFieldDto createDto() throws Exception;

}
