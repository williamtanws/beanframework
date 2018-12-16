package com.beanframework.employee.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.domain.Employee;

public interface EmployeeFacade {

	Employee saveProfile(Employee employee, MultipartFile picture) throws Exception;

	Employee updatePrincipal(Employee employee);

	Set<EmployeeSession> findAllSessions();

	void expireAllSessionsByUuid(UUID uuid);

	void expireAllSessions();

	Page<Employee> page(Employee employee, int page, int size, Direction direction, String... properties);

	Employee getCurrentEmployee();

	Employee authenticate(String id, String password);
	
	void deleteEmployeeProfilePictureByUuid(UUID uuid);

	void deleteAllEmployeeProfilePicture();

}
