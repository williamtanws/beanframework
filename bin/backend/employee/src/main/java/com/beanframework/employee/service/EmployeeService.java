package com.beanframework.employee.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.employee.domain.Employee;

public interface EmployeeService {

	Employee updatePrincipal(Employee employee);

	Page<Employee> page(Employee employee, Pageable pageable);

	Employee authenticate(String id, String password);

	void saveProfilePicture(Employee employee, MultipartFile picture) throws IOException;

	void deleteEmployeeProfilePictureByUuid(UUID uuid);

	void deleteAllEmployeeProfilePicture();

	Employee getCurrentEmployee();

}
