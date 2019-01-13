package com.beanframework.employee.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

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

	@PreAuthorize(EmployeePreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(EmployeePreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.READ)
	List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

}
