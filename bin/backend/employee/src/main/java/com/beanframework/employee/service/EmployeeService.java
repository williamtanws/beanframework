package com.beanframework.employee.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserGroup;

public interface EmployeeService {

	void saveProfilePicture(Employee employee, MultipartFile picture) throws IOException;

	void deleteEmployeeProfilePictureByUuid(UUID uuid);

	void deleteAllEmployeeProfilePicture();

	List<Employee> findEntityBySorts(Map<String, Direction> employeeSorts) throws Exception;
	
	Employee create() throws Exception;
	
	Employee saveEntity(Employee employee) throws BusinessException;

	Employee findAuthenticate(String id, String password) throws Exception;

	Employee getCurrentUser() throws Exception;

	Employee updatePrincipal(Employee employee);

	Set<GrantedAuthority> getAuthorities(List<UserGroup> userGroups, Set<String> processedUserGroupUuids);

	Employee findCachedOneEntityByUuid(UUID uuid) throws Exception;

	void deleteByUuid(UUID uuid) throws BusinessException;

}
