package com.beanframework.employee.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.employee.domain.Employee;

public interface EmployeeService {

	Employee create();

	Employee initDefaults(Employee employee);

	Employee save(Employee employee);

	Employee saveProfile(Employee employee, MultipartFile picture) throws IOException;

	Employee updatePrincipal(Employee employee);

	void delete(UUID uuid);

	void delete(String id);

	void deleteAll();

	Optional<Employee> findEntityByUuid(UUID uuid);

	Optional<Employee> findEntityById(String id);

	Employee findByUuid(UUID uuid);

	Employee findById(String id);

	Page<Employee> page(Employee employee, Pageable pageable);

	Employee getCurrentEmployee();

	Employee authenticate(String id, String password);

}
