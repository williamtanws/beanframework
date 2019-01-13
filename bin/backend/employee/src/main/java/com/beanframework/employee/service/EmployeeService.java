package com.beanframework.employee.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.employee.domain.Employee;

public interface EmployeeService {

	Employee updatePrincipal(Employee employee);

	Employee findDtoAuthenticate(String id, String password) throws Exception;

	void saveProfilePicture(Employee employee, MultipartFile picture) throws IOException;

	void deleteEmployeeProfilePictureByUuid(UUID uuid);

	void deleteAllEmployeeProfilePicture();

	Employee getCurrentEmployee();

	List<Employee> findDtoBySorts(Map<String, Direction> employeeSorts) throws Exception;
	
	Employee saveProfile(Employee employee, MultipartFile picture) throws BusinessException;

	Employee create() throws Exception;

	Employee getCurrentUser();
	
	Employee saveEntity(Employee model) throws BusinessException;

}
