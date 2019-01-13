package com.beanframework.customer.service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.customer.domain.Customer;

public interface CustomerService {

	Customer findDtoAuthenticate(String id, String password) throws Exception;

	Customer getCurrentUser();

	Customer create() throws Exception;

	Customer saveEntity(Customer model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

}
