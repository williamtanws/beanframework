package com.beanframework.customer.service;

import com.beanframework.customer.domain.Customer;

public interface CustomerService {

	Customer findDtoAuthenticate(String id, String password) throws Exception;

}
