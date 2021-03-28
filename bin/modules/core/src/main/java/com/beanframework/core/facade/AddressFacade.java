package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.AddressDto;

public interface AddressFacade {

	AddressDto findOneByUuid(UUID uuid) throws Exception;

	AddressDto findOneProperties(Map<String, Object> properties) throws Exception;

	AddressDto create(AddressDto model) throws BusinessException;

	AddressDto update(AddressDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<AddressDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	AddressDto createDto() throws Exception;
}
