package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserPermission;

public interface UserPermissionService {

	UserPermission create() throws Exception;

	UserPermission findOneEntityByUuid(UUID uuid) throws Exception;

	UserPermission findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<UserPermission> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	<T> Page<UserPermission> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	UserPermission saveEntity(UserPermission model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;
}
