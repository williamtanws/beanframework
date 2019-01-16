package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.User;

public interface AuditorService {

	Auditor create() throws Exception;

	Auditor findOneEntityByUuid(UUID uuid) throws Exception;

	Auditor findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Auditor> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	<T> Page<Auditor> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	Auditor saveEntity(User model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;
}
