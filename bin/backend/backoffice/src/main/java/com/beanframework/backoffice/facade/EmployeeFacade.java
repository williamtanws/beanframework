package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.backoffice.data.EmployeeDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.employee.EmployeeSession;

public interface EmployeeFacade {

	public static interface EmployeeDtoPreAuthorizeEnum {
		public static final String READ = "hasAuthority('employee_read')";
		public static final String CREATE = "hasAuthority('employee_create')";
		public static final String UPDATE = "hasAuthority('employee_update')";
		public static final String DELETE = "hasAuthority('employee_delete')";
	}

	public static interface EmployeeDtoSessionPreAuthorizeEnum {
		public static final String READ = "hasAuthority('employeesession_read')";
		public static final String DELETE = "hasAuthority('employeesession_delete')";
	}

	@PreAuthorize(EmployeeDtoSessionPreAuthorizeEnum.READ)
	Set<EmployeeSession> findAllSessions();

	@PreAuthorize(EmployeeDtoSessionPreAuthorizeEnum.DELETE)
	void expireAllSessionsByUuid(UUID uuid);

	@PreAuthorize(EmployeeDtoSessionPreAuthorizeEnum.DELETE)
	void expireAllSessions();

	@PreAuthorize(EmployeeDtoPreAuthorizeEnum.READ)
	Page<EmployeeDto> findPage(Specification<EmployeeDto> specification, PageRequest pageRequest) throws Exception;

	@PreAuthorize(EmployeeDtoPreAuthorizeEnum.READ)
	EmployeeDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(EmployeeDtoPreAuthorizeEnum.READ)
	EmployeeDto findOneByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(EmployeeDtoPreAuthorizeEnum.CREATE)
	EmployeeDto create(EmployeeDto model) throws BusinessException;

	@PreAuthorize(EmployeeDtoPreAuthorizeEnum.UPDATE)
	EmployeeDto update(EmployeeDto model) throws BusinessException;

	@PreAuthorize(EmployeeDtoPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(EmployeeDtoPreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	@PreAuthorize(EmployeeDtoPreAuthorizeEnum.READ)
	List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	EmployeeDto saveProfile(EmployeeDto employee, MultipartFile picture) throws BusinessException;

	List<EmployeeDto> findAllDtoEmployees() throws Exception;

	EmployeeDto getProfile() throws Exception;

}
