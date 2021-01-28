package com.beanframework.user.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.user.EmployeeSession;
import com.beanframework.user.domain.Employee;

public interface EmployeeService {

	void saveProfilePicture(Employee model, MultipartFile picture) throws IOException;

	void saveProfilePicture(Employee model, InputStream inputStream) throws IOException;

	void deleteEmployeeProfilePictureByUuid(UUID uuid);

	Employee findAuthenticate(String id, String password) throws Exception;

	Employee getCurrentUser() throws Exception;

	Employee updatePrincipal(Employee model);

	Set<EmployeeSession> findAllSessions();

	void expireAllSessionsByUuid(UUID uuid);

	void expireAllSessions();
}
