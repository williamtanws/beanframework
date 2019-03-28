package com.beanframework.customer.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.customer.domain.Customer;

public interface CustomerService {

	Customer create() throws Exception;

	Customer findOneEntityByUuid(UUID uuid) throws Exception;

	Customer findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Customer> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	Customer saveEntity(Customer model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	Page<Customer> findEntityPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	void saveProfilePicture(Customer model, MultipartFile picture) throws IOException;

	void saveProfilePicture(Customer model, InputStream inputStream) throws IOException;

	Customer updatePrincipal(Customer model);

	Customer getCurrentUser() throws Exception;
}
