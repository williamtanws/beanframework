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

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.InterceptorException;

@SuppressWarnings("rawtypes")
public interface ModelService {

	void attach(Object model);

	void detach(Object model);

	void detachAll();

	<T> T merge(Object model);

	void refresh(Object model);

	void flush() throws BusinessException;

	<T> T create(Class modelClass) throws Exception;

	@Cacheable(value = "Model", key = "'uuid:'+#uuid+',modelClass:'+#modelClass")
	<T> T findByUuid(UUID uuid, Class modelClass) throws Exception;

	@Cacheable(value = "ModelProperties", key = "'properties:'+#properties+',modelClass:'+#modelClass")
	<T> T findByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	@Cacheable(value = "ModelCount", key = "'modelClass:'+#modelClass")
	int countAll(Class modelClass) throws Exception;

	@Cacheable(value = "ModelCount", key = "'properties:'+#properties+',modelClass:'+#modelClass")
	int countByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	@Cacheable(value = "ModelCount", key = "'specification:'+#specification+',modelClass:'+#modelClass")
	int countBySpecification(Specification specification, Class modelClass) throws Exception;

	@Cacheable(value = "ModelPropertiesExists", key = "'properties:'+#properties+',modelClass:'+#modelClass")
	boolean existsByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	/**
	 * 
	 * Properties doesn't work with many to many or one to many. Only many to one or
	 * one to one.
	 * 
	 * @param properties
	 * @param sorts
	 * @param firstResult
	 * @param maxResult
	 * @param modelClass
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value = "ModelList", key = "'properties:'+#properties+',sorts:'+#sorts+',firstResult:'+#firstResult+',maxResult:'+#maxResult+',modelClass:'+#modelClass")
	<T extends Collection> T findByPropertiesBySortByResult(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Integer firstResult, Integer maxResult, Class modelClass)
			throws Exception;

	@Cacheable(value = "ModelList", key = "'specification:'+#specification+',sorts:'+#sorts+',modelClass:'+#modelClass")
	<T extends Collection> T findBySpecificationBySort(Specification specification, Sort sort, Class modelClass) throws Exception;

	@Cacheable(value = "ModelList", key = "modelClass:'+#modelClass")
	<T extends Collection> T findAll(Class modelClass);

//	@Cacheable(value = "ModelHistoryList", key = "'selectDeletedEntities:'+#selectDeletedEntities+',auditCriterions:'+#auditCriterions+',auditOrders:'+#auditOrders+',firstResult:'+#firstResult+',maxResults:'+#maxResults+',modelClass:'+#modelClass")
	List<Object[]> findHistory(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer firstResult, Integer maxResults, Class modelClass)
			throws Exception;

//	@Cacheable(value = "ModelHistoryCount", key = "'selectDeletedEntities:'+#selectDeletedEntities+',auditCriterions:'+#auditCriterions+',auditOrders:'+#auditOrders+',firstResult:'+#firstResult+',maxResults:'+#maxResults+',modelClass:'+#modelClass")
	int countHistory(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer firstResult, Integer maxResults, Class modelClass) throws Exception;

	@Cacheable(value = "ModelPage", key = "'spec:'+#spec+',pageable:'+#pageable+',modelClass:'+#modelClass")
	<T> Page<T> findPage(Specification specification, Pageable pageable, Class modelClass) throws Exception;

	@Caching(evict = { //
			@CacheEvict(value = "Model", key = "'uuid:'+#model.uuid+',modelClass:'+#modelClass", condition = "#model.uuid != null"), //
			@CacheEvict(value = "ModelProperties", allEntries = true), //
			@CacheEvict(value = "ModelCount", key = "'modelClass:'+#modelClass"), //
			@CacheEvict(value = "ModelPropertiesExists", allEntries = true), //
			@CacheEvict(value = "ModelList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryCount", allEntries = true), //
			@CacheEvict(value = "ModelPage", allEntries = true) })
	<T> T saveEntity(Object model, Class modelClass) throws BusinessException;

	@Caching(evict = { //
			@CacheEvict(value = "Model", allEntries = true), //
			@CacheEvict(value = "ModelProperties", allEntries = true), //
			@CacheEvict(value = "ModelCount", key = "'modelClass:'+#modelClass"), //
			@CacheEvict(value = "ModelPropertiesExists", allEntries = true), //
			@CacheEvict(value = "ModelList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryCount", allEntries = true), //
			@CacheEvict(value = "ModelPage", allEntries = true) })
	void deleteByEntity(Object entityModel, Class modelClass) throws BusinessException;

	@Caching(evict = { //
			@CacheEvict(value = "Model", key = "'uuid:'+#uuid+',modelClass:'+#modelClass", condition = "#uuid != null"), //
			@CacheEvict(value = "ModelProperties", allEntries = true), //
			@CacheEvict(value = "ModelCount", key = "'modelClass:'+#modelClass"), //
			@CacheEvict(value = "ModelPropertiesExists", allEntries = true), //
			@CacheEvict(value = "ModelList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryList", allEntries = true), //
			@CacheEvict(value = "ModelHistoryCount", allEntries = true), //
			@CacheEvict(value = "ModelPage", allEntries = true) })
	void deleteByUuid(UUID uuid, Class modelClass) throws BusinessException;

	<T> T getEntity(Object model, Class modelClass) throws Exception;

	<T> T getEntity(Object model, Class modelClass, EntityConverterContext context) throws Exception;

	<T extends Collection> T getEntity(Collection model, Class modelClass) throws Exception;

	<T extends Collection> T getEntity(Collection model, Class modelClass, EntityConverterContext context) throws Exception;

	<T> T getDto(Object model, Class modelClass) throws Exception;

	<T> T getDto(Object model, Class modelClass, DtoConverterContext context) throws Exception;

	<T extends Collection> T getDto(Collection models, Class modelClass) throws Exception;

	<T extends Collection> T getDto(Collection models, Class modelClass, DtoConverterContext context) throws Exception;

	void initDefaults(Object model, Class modelClass) throws Exception;

	<T> Page<T> page(Specification spec, Pageable pageable, Class modelClass);

	void initialDefaultsInterceptor(Collection models, InterceptorContext context, String typeCode) throws InterceptorException;

	void initialDefaultsInterceptor(Object model, InterceptorContext context, String typeCode) throws InterceptorException;

	void loadInterceptor(Collection models, InterceptorContext context, String typeCode) throws InterceptorException;

	void loadInterceptor(Object model, InterceptorContext context, String typeCode) throws InterceptorException;

	void prepareInterceptor(Collection models, InterceptorContext context, String typeCode) throws InterceptorException;

	void prepareInterceptor(Object model, InterceptorContext context, String typeCode) throws InterceptorException;

	void removeInterceptor(Collection models, InterceptorContext context, String typeCode) throws InterceptorException;

	void removeInterceptor(Object model, InterceptorContext context, String typeCode) throws InterceptorException;

	void validateInterceptor(Collection models, InterceptorContext context, String typeCode) throws InterceptorException;

	void validateInterceptor(Object model, InterceptorContext context, String typeCode) throws InterceptorException;

	void entityConverter(Collection models, EntityConverterContext context, Class modelClass) throws ConverterException;

	<T> T entityConverter(Object model, EntityConverterContext context, Class modelClass) throws ConverterException;

	<T extends Collection> T dtoConverter(Collection models, DtoConverterContext context, String typeCode) throws ConverterException, InterceptorException;

	<T> T dtoConverter(Object model, DtoConverterContext context, String typeCode) throws ConverterException;

}
