package com.beanframework.employee.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.domain.Employee;

public interface EmployeeFacade {

	Employee saveProfile(Employee employee, MultipartFile picture) throws BusinessException;

	Employee updatePrincipal(Employee employee);

	Set<EmployeeSession> findAllSessions();

	void expireAllSessionsByUuid(UUID uuid);

	void expireAllSessions();

	Employee getCurrentEmployee();

	Employee authenticate(String id, String password) throws BusinessException, Exception;
	
	void deleteEmployeeProfilePictureByUuid(UUID uuid);

	void deleteAllEmployeeProfilePicture();

}
