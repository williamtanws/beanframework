package com.beanframework.customer.service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.customer.domain.Customer;

public interface CustomerService {

	Customer create() throws Exception;

	Customer saveEntity(Customer model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

}
