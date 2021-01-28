package com.beanframework.core.facade;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserDto;

public interface UserFacade {

	UserDto findOneByUuid(UUID uuid) throws Exception;

	UserDto getCurrentUser() throws Exception;

	UserDto saveProfile(UserDto user) throws BusinessException;

	Page<UserDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;
}
