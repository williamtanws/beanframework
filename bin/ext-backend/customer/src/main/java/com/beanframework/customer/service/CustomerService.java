package com.beanframework.customer.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.customer.domain.Customer;

public interface CustomerService {

	Customer create();

	Customer initDefaults(Customer customer);

	Customer save(Customer customer);

	void delete(UUID uuid);
	
	void deleteById(String id);

	void deleteAll();
	
	Optional<Customer> findEntityByUuid(UUID uuid);

	Optional<Customer> findEntityById(String id);

	Customer findByUuid(UUID uuid);

	Customer findById(String id);
	
	boolean existsById(String id);

	Page<Customer> page(Customer customer, Pageable pageable);

	Customer authenticate(String id, String password);

}
