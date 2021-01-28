package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserRightDto;

public interface UserRightFacade {

	UserRightDto findOneByUuid(UUID uuid) throws Exception;

	UserRightDto findOneProperties(Map<String, Object> properties) throws Exception;

	UserRightDto create(UserRightDto model) throws BusinessException;

	UserRightDto update(UserRightDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<UserRightDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	UserRightDto createDto() throws Exception;

	List<UserRightDto> findAllDtoUserRights() throws Exception;
}
