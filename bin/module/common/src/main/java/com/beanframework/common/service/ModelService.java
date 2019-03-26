package com.beanframework.common.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
	
	@Cacheable(value = "Model", key = "'uuid:'+#uuid+',modelClass:'+#modelClass")
	<T> T findOneEntityByUuid(UUID uuid, Class modelClass) throws Exception;

	@Cacheable(value = "ModelProperties", key = "'properties:'+#properties+',modelClass:'+#modelClass")
	<T> T findOneEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	@Cacheable(value = "ModelCount", key = "'modelClass:'+#modelClass")
	int count(Class modelClass) throws Exception;

	@Cacheable(value = "ModelPropertiesCount", key = "'properties:'+#properties+',modelClass:'+#modelClass")
	int count(Map<String, Object> properties, Class modelClass) throws Exception;
	
	@Cacheable(value = "ModelPropertiesExists", key = "'properties:'+#properties+',modelClass:'+#modelClass")
	boolean existsByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	@Cacheable(value = "ModelList", key = "'properties:'+#properties+',sorts:'+#sorts+',firstResult:'+#firstResult+',maxResult:'+#maxResult+',modelClass:'+#modelClass")
	<T extends Collection> T findEntityByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Integer firstResult, Integer maxResult, Class modelClass)
			throws Exception;

	@Cacheable(value = "ModelHistoryList", key = "'selectDeletedEntities:'+#selectDeletedEntities+',auditCriterions:'+#auditCriterions+',auditOrders:'+#auditOrders+',firstResult:'+#firstResult+',maxResults:'+#maxResults+',modelClass:'+#modelClass")
	List<Object[]> findHistories(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer firstResult, Integer maxResults, Class modelClass)
			throws Exception;

	@Cacheable(value = "ModelHistoryCount", key = "'selectDeletedEntities:'+#selectDeletedEntities+',auditCriterions:'+#auditCriterions+',auditOrders:'+#auditOrders+',firstResult:'+#firstResult+',maxResults:'+#maxResults+',modelClass:'+#modelClass")
	int findCountHistory(boolean b, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer start, Integer length, Class modelClass) throws Exception;
	
	@Cacheable(value = "ModelPage", key = "'spec:'+#spec+',pageable:'+#pageable+',modelClass:'+#modelClass")
	<T> Page<T> findEntityPage(Specification specification, Pageable pageable, Class modelClass) throws Exception;

	void refresh(Object model);

	@Caching(evict = { //
			@CacheEvict(value = "Model", key = "'uuid:'+#model.uuid+',modelClass:'+#modelClass", condition = "#model.uuid != null"), //
			@CacheEvict(value = "ModelProperties", allEntries = true), //
			@CacheEvict(value = "ModelCount", key = "'modelClass:'+#modelClass"), //
			@CacheEvict(value = "ModelPropertiesCount", allEntries = true), //
			@CacheEvict(value = "ModelPropertiesExists", allEntries = true), //
			@CacheEvict(value = "ModelList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryCount", allEntries = true), //
			@CacheEvict(value = "ModelPage", allEntries = true) })
	Object saveEntity(Object model, Class modelClass) throws BusinessException;

	void flush() throws BusinessException;

	void deleteByEntity(Object entityModel, Class modelClass) throws BusinessException;

	@Caching(evict = { //
			@CacheEvict(value = "Model", key = "'uuid:'+#uuid+',modelClass:'+#modelClass", condition = "#uuid != null"), //
			@CacheEvict(value = "ModelProperties", allEntries = true), //
			@CacheEvict(value = "ModelCount", key = "'modelClass:'+#modelClass"), //
			@CacheEvict(value = "ModelPropertiesCount", allEntries = true), //
			@CacheEvict(value = "ModelPropertiesExists", allEntries = true), //
			@CacheEvict(value = "ModelList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryCount", allEntries = true), //
			@CacheEvict(value = "ModelPage", allEntries = true) })
	void deleteByUuid(UUID uuid, Class modelClass) throws BusinessException;

	<T> T getEntity(Object model, Class modelClass) throws Exception;

	<T extends Collection> T getEntity(Collection model, Class modelClass) throws Exception;

	<T> T getDto(Object model, Class modelClass) throws Exception;

	<T extends Collection> T getDto(Collection models, Class modelClass) throws Exception;

	void initDefaults(Object model, Class modelClass) throws Exception;

}
