package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.backoffice.data.EmployeeDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.employee.EmployeeSession;

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

	@PreAuthorize(EmployeePreAuthorizeEnum.READ)
	EmployeeDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.READ)
	EmployeeDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.CREATE)
	EmployeeDto create(EmployeeDto model) throws BusinessException;

	@PreAuthorize(EmployeePreAuthorizeEnum.UPDATE)
	EmployeeDto update(EmployeeDto model) throws BusinessException;

	@PreAuthorize(EmployeePreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<EmployeeDto> findPage(DataTableRequest<EmployeeDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<EmployeeDto> findAllDtoEmployees() throws Exception;

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.READ)
	Set<EmployeeSession> findAllSessions();

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.DELETE)
	void expireAllSessionsByUuid(UUID uuid);

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.DELETE)
	void expireAllSessions();

	EmployeeDto saveProfile(EmployeeDto employee, MultipartFile picture) throws BusinessException;

	EmployeeDto getProfile() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

}
