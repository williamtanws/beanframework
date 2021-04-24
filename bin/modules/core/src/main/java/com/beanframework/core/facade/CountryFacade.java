package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CountryDto;

public interface CountryFacade {

	CountryDto findOneByUuid(UUID uuid) throws Exception;

	CountryDto findOneProperties(Map<String, Object> properties) throws Exception;

	CountryDto create(CountryDto model) throws BusinessException;

	CountryDto update(CountryDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<CountryDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	CountryDto createDto() throws Exception;
}
