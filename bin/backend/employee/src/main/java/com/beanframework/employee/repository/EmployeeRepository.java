package com.beanframework.employee.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.employee.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {

	Optional<Employee> findByUuid(UUID uuid);

	Optional<Employee> findById(String id);

	void deleteById(String id);
}
