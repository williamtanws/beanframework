package com.beanframework.employee.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.domain.EmployeeSession;

public interface EmployeeFacade {

	Employee create();

	Employee initDefaults(Employee employee);

	Employee save(Employee employee, Errors bindingResult);

	Employee saveProfile(Employee employee, MultipartFile picture, Errors bindingResult);

	Employee updatePrincipal(Employee employee);

	void delete(UUID uuid, Errors bindingResult);

	void delete(String id, Errors bindingResult);

	void deleteAll();

	Employee findByUuid(UUID uuid);

	Employee findById(String id);

	Set<EmployeeSession> findAllSessions();

	void expireAllSessionsByUuid(UUID uuid);

	void expireAllSessions();

	Page<Employee> page(Employee employee, int page, int size, Direction direction, String... properties);

	Employee getCurrentEmployee();

	Employee authenticate(String id, String password);

}
