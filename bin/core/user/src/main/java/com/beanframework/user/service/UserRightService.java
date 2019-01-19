package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserRight;

public interface UserRightService {

	UserRight create() throws Exception;

	UserRight findOneEntityByUuid(UUID uuid) throws Exception;

	UserRight findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<UserRight> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	<T> Page<UserRight> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	UserRight saveEntity(UserRight model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;
}
