package com.beanframework.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.customer.domain.Customer;

public interface CustomerFacade {

	Page<Customer> page(Customer customer, int page, int size, Direction direction, String... properties);

	Customer getCurrentCustomer();

	Customer authenticate(String id, String password);
}
