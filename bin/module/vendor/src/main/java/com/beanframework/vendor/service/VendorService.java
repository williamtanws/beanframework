package com.beanframework.vendor.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.vendor.domain.Vendor;

public interface VendorService {

	Vendor create() throws Exception;

	Vendor findOneEntityByUuid(UUID uuid) throws Exception;

	Vendor findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Vendor> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	Vendor saveEntity(Vendor model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<Vendor> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
