package com.beanframework.customer.service;

import com.beanframework.customer.domain.Customer;

public interface CustomerFacade {

	Customer getCurrentCustomer();

	Customer authenticate(String id, String password) throws Exception;
}
