package com.beanframework.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserGroup;

public interface UserGroupService {

	UserGroup create() throws Exception;
	
	List<UserGroup> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	UserGroup saveEntity(UserGroup model) throws BusinessException;

	void deleteById(String id) throws BusinessException;
}
