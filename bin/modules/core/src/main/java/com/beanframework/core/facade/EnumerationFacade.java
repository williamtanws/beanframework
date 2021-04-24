package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EnumerationDto;

public interface EnumerationFacade {

	EnumerationDto findOneByUuid(UUID uuid) throws Exception;

	EnumerationDto findOneProperties(Map<String, Object> properties) throws Exception;

	EnumerationDto create(EnumerationDto model) throws BusinessException;

	EnumerationDto update(EnumerationDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<EnumerationDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	EnumerationDto createDto() throws Exception;
}
