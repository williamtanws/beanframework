package com.beanframework.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserRight;

public interface UserRightService {

	UserRight create() throws Exception;

	List<UserRight> findDtoBySorts(Map<String, Direction> sorts) throws Exception;

	UserRight saveEntity(UserRight model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

}
