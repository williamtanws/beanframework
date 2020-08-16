package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.user.EmployeeSession;

public interface EmployeeFacade {

	public static interface EmployeePreAuthorizeEnum {

		public static final String AUTHORITY_READ = "employee_read";
		public static final String AUTHORITY_CREATE = "employee_create";
		public static final String AUTHORITY_UPDATE = "employee_update";
		public static final String AUTHORITY_DELETE = "employee_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	public static interface EmployeeSessionPreAuthorizeEnum {

		public static final String AUTHORITY_READ = "employeesession_read";
		public static final String AUTHORITY_DELETE = "employeesession_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_READ)
	EmployeeDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_READ)
	EmployeeDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_CREATE)
	EmployeeDto create(EmployeeDto model) throws BusinessException;

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_UPDATE)
	EmployeeDto update(EmployeeDto model) throws BusinessException;

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_READ)
	Page<EmployeeDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.HAS_READ)
	Set<EmployeeSession> findAllSessions();

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.HAS_DELETE)
	void expireAllSessionsByUuid(UUID uuid);

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.HAS_DELETE)
	void expireAllSessions();

	@PreAuthorize(EmployeePreAuthorizeEnum.HAS_CREATE)
	EmployeeDto createDto() throws Exception;

	EmployeeDto saveProfile(EmployeeDto employee) throws BusinessException;

	EmployeeDto getCurrentUser() throws Exception;
}
