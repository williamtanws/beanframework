package com.beanframework.common.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;

@SuppressWarnings("rawtypes")
public interface ModelService {

	void attach(Object model, Class modelClass);

	void detach(Object model, Class modelClass);

	void detachAll();

	<T> T create(Class modelClass) throws Exception;

	<T> T findOneEntityByUuid(UUID uuid, Class modelClass) throws Exception;

	<T> T findOneDtoByUuid(UUID uuid, Class modelClass) throws Exception;

	<T> T findOneEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T> T findOneDtoByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findEntityBySorts(Map<String, Sort.Direction> sorts, Class modelClass) throws Exception;

	<T extends Collection> T findEntityByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Class modelClass) throws Exception;

	<T extends Collection> T findEntityByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts, int maxResult, Class modelClass) throws Exception;

	<T extends Collection> T findDtoByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findDtoBySorts(Map<String, Sort.Direction> sorts, Class modelClass) throws Exception;

	<T extends Collection> T findDtoByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Class modelClass) throws Exception;

	boolean existsByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findAllDto(Class modelClass) throws Exception;

	<T extends Collection> T findHistory(boolean selectDeletedEntities, AuditCriterion criterion, AuditOrder order, Integer firstResult, Integer maxResults, Class modelClass) throws Exception;

	<T> Page<T> findDtoPage(Specification specification, Pageable pageable, Class modelClass) throws Exception;

	void refresh(Object model);

	Object saveEntity(Object model, Class modelClass) throws BusinessException;

	Object saveDto(Object model, Class modelClass) throws BusinessException;

	void flush() throws BusinessException;

	void deleteByEntity(Object entityModel, Class modelClass) throws BusinessException;

	void deleteByUuid(UUID uuid, Class modelClass) throws BusinessException;

	void deleteAll(Class modelClass) throws BusinessException;

	<T> T getEntity(Object model, Class modelClass) throws Exception;

	<T extends Collection> T getEntity(Collection model, Class modelClass) throws Exception;

	<T> T getDto(Object model, Class modelClass) throws Exception;

	<T extends Collection> T getDto(Collection models, Class modelClass) throws Exception;

	void initDefaults(Object model, Class modelClass) throws Exception;

	public void clearCache(Class modelClass);
}
