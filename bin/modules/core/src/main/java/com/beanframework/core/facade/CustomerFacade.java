package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CustomerDto;

public interface CustomerFacade {

	CustomerDto findOneByUuid(UUID uuid) throws Exception;

	CustomerDto findOneProperties(Map<String, Object> properties) throws Exception;

	CustomerDto create(CustomerDto model) throws BusinessException;

	CustomerDto update(CustomerDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<CustomerDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	CustomerDto createDto() throws Exception;

	CustomerDto getCurrentUser() throws Exception;

}
