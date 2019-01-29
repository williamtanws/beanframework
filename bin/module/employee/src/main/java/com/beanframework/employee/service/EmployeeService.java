package com.beanframework.employee.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserGroup;

public interface EmployeeService {

	Employee create() throws Exception;

	Employee findOneEntityByUuid(UUID uuid) throws Exception;

	Employee findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Employee> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	Employee saveEntity(Employee model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<Employee> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	void saveProfilePicture(Employee employee, MultipartFile picture) throws IOException;

	void deleteEmployeeProfilePictureByUuid(UUID uuid);

	Employee findAuthenticate(String id, String password) throws Exception;

	Employee getCurrentUser() throws Exception;

	Employee updatePrincipal(Employee employee);

	Set<GrantedAuthority> getAuthorities(List<UserGroup> userGroups, Set<String> processedUserGroupUuids);

	Set<EmployeeSession> findAllSessions();

	void expireAllSessionsByUuid(UUID uuid);

	void expireAllSessions();

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

}
