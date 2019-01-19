package com.beanframework.language.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.language.domain.Language;

public interface LanguageService {

	Language create() throws Exception;

	Language findOneEntityByUuid(UUID uuid) throws Exception;

	Language findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Language> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	<T> Page<Language> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	Language saveEntity(Language model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;
}
