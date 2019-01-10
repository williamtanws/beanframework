package com.beanframework.employee.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.domain.Employee;

public interface EmployeeFacade {

	public static interface EmployeePreAuthorizeEnum {
		public static final String READ = "hasAuthority('employee_read')";
		public static final String CREATE = "hasAuthority('employee_create')";
		public static final String UPDATE = "hasAuthority('employee_update')";
		public static final String DELETE = "hasAuthority('employee_delete')";
	}

	public static interface EmployeeSessionPreAuthorizeEnum {
		public static final String READ = "hasAuthority('employeesession_read')";
		public static final String DELETE = "hasAuthority('employeesession_delete')";
	}

	Employee saveProfile(Employee employee, MultipartFile picture) throws BusinessException;

	void updatePrincipal(Employee employee);

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.READ)
	Set<EmployeeSession> findAllSessions();

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.DELETE)
	void expireAllSessionsByUuid(UUID uuid);

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.DELETE)
	void expireAllSessions();

	@PreAuthorize(EmployeePreAuthorizeEnum.READ)
	Page<Employee> findPage(Specification<Employee> specification, PageRequest pageRequest) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.READ)
	Employee findOneDtoByUuid(UUID uuid) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.READ)
	Employee findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.CREATE)
	Employee createDto(Employee model) throws BusinessException;

	@PreAuthorize(EmployeePreAuthorizeEnum.UPDATE)
	Employee updateDto(Employee model) throws BusinessException;

	Employee saveEntity(Employee model) throws BusinessException;

	@PreAuthorize(EmployeePreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Employee create() throws Exception;

	Employee getCurrentUser();

	Employee findDtoAuthenticate(String id, String password) throws BusinessException, Exception;

	void deleteEmployeeProfilePictureByUuid(UUID uuid);

	void deleteAllEmployeeProfilePicture();

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<Employee> findDtoBySorts(Map<String, Direction> employeeSorts) throws Exception;

}
