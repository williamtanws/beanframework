package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CompanyDto;

public interface CompanyFacade {

	CompanyDto findOneByUuid(UUID uuid) throws Exception;

	CompanyDto findOneProperties(Map<String, Object> properties) throws Exception;

	CompanyDto create(CompanyDto model) throws BusinessException;

	CompanyDto update(CompanyDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<CompanyDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	CompanyDto createDto() throws Exception;

}
