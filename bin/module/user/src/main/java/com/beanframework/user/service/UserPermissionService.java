package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserPermission;

public interface UserPermissionService {

	UserPermission findOneEntityByUuid(UUID uuid) throws Exception;

	UserPermission findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<UserPermission> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	UserPermission saveEntity(UserPermission model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	Page<UserPermission> findEntityPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
