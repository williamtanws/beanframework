package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.AdminDto;

public interface AdminFacade {

	AdminDto findOneByUuid(UUID uuid) throws Exception;

	AdminDto findOneProperties(Map<String, Object> properties) throws Exception;

	AdminDto create(AdminDto model) throws BusinessException;

	AdminDto update(AdminDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<AdminDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	AdminDto createDto() throws Exception;
}
