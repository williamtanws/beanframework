package com.beanframework.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserPermission;

public interface UserPermissionService {

	UserPermission create() throws Exception;

	List<UserPermission> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	UserPermission saveEntity(UserPermission model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

}
