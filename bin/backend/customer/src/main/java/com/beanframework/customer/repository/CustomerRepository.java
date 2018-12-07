package com.beanframework.customer.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.customer.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

	Optional<Customer> findByUuid(UUID uuid);

	Optional<Customer> findById(String id);

	boolean existsById(String id);

	void deleteById(String id);
}
