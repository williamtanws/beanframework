package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserPermissionDto;

public interface UserPermissionFacade {

	UserPermissionDto findOneByUuid(UUID uuid) throws Exception;

	UserPermissionDto findOneProperties(Map<String, Object> properties) throws Exception;

	UserPermissionDto create(UserPermissionDto model) throws BusinessException;

	UserPermissionDto update(UserPermissionDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<UserPermissionDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	UserPermissionDto createDto() throws Exception;

	List<UserPermissionDto> findAllDtoUserPermissions() throws Exception;

}
