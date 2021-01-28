package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.RegionDto;

public interface RegionFacade {

	RegionDto findOneByUuid(UUID uuid) throws Exception;

	RegionDto findOneProperties(Map<String, Object> properties) throws Exception;

	RegionDto create(RegionDto model) throws BusinessException;

	RegionDto update(RegionDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<RegionDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	RegionDto createDto() throws Exception;
}
