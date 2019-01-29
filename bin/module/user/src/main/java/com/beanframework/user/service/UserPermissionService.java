package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserPermission;

public interface UserPermissionService {

	UserPermission create() throws Exception;

	UserPermission findOneEntityByUuid(UUID uuid) throws Exception;

	UserPermission findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<UserPermission> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	UserPermission saveEntity(UserPermission model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<UserPermission> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}