package com.beanframework.admin.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface AdminService {

	Admin create() throws Exception;

	Admin findOneEntityByUuid(UUID uuid) throws Exception;

	Admin findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Admin> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	Admin saveEntity(Admin model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	Admin findAuthenticate(String id, String password) throws Exception;

	Admin getCurrentUser() throws Exception;

	Page<Admin> findEntityPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

}
