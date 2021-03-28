package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserGroupDto;

public interface UserGroupFacade {

	UserGroupDto findOneByUuid(UUID uuid) throws Exception;

	UserGroupDto findOneProperties(Map<String, Object> properties) throws Exception;

	UserGroupDto create(UserGroupDto model) throws BusinessException;

	UserGroupDto update(UserGroupDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<UserGroupDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	UserGroupDto createDto() throws Exception;
}
