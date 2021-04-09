package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmployeeDto;

public interface EmployeeFacade {

	EmployeeDto findOneByUuid(UUID uuid) throws Exception;

	EmployeeDto findOneProperties(Map<String, Object> properties) throws Exception;

	EmployeeDto create(EmployeeDto model) throws BusinessException;

	EmployeeDto update(EmployeeDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<EmployeeDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	EmployeeDto createDto() throws Exception;

	EmployeeDto saveProfile(EmployeeDto employee) throws BusinessException;

	EmployeeDto getCurrentUser() throws Exception;
}