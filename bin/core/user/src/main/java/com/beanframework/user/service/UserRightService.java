package com.beanframework.user.service;

import java.util.UUID;

import com.beanframework.common.exception.BusinessException;

public interface UserRightService {

	void delete(UUID uuid) throws BusinessException;

}
