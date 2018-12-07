package com.beanframework.customer.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.customer.domain.Customer;

public interface CustomerFacade {

	Customer create();

	Customer initDefaults(Customer customer);

	Customer save(Customer customer, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);
	
	void delete(String id, Errors bindingResult);
	
	void deleteAll();

	Customer findByUuid(UUID uuid);

	Customer findById(String id);
	
	boolean existsById(String id);

	Page<Customer> page(Customer customer, int page, int size, Direction direction, String... properties);

	Customer getCurrentCustomer();

	Customer authenticate(String id, String password);
}
