package com.beanframework.admin.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.exception.BusinessException;

public interface AdminService {

	Admin create() throws Exception;

	Admin findOneEntityByUuid(UUID uuid) throws Exception;

	Admin findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Admin> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	<T> Page<Admin> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	Admin saveEntity(Admin model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	Admin findAuthenticate(String id, String password) throws Exception;

	Admin getCurrentUser() throws Exception;

}
