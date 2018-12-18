package com.beanframework.employee.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.employee.domain.Employee;

public interface EmployeeService {

	Employee updatePrincipal(Employee employee);

	Employee authenticate(String id, String password);

	void saveProfilePicture(Employee employee, MultipartFile picture) throws IOException;

	void deleteEmployeeProfilePictureByUuid(UUID uuid);

	void deleteAllEmployeeProfilePicture();

	Employee getCurrentEmployee();

}
