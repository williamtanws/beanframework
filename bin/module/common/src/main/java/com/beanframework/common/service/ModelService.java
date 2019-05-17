package com.beanframework.common.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
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

	<T> T findOneByUuid(UUID uuid, Class modelClass) throws Exception;

	<T> T findOneByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	int countAll(Class modelClass) throws Exception;

	int countByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	int countBySpecification(Specification specification, Class modelClass) throws Exception;

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
	<T extends Collection> T findByPropertiesBySortByResult(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Integer firstResult, Integer maxResult, Class modelClass)
			throws Exception;

	<T extends Collection> T findBySpecificationBySort(Specification specification, Sort sort, Class modelClass) throws Exception;

	<T extends Collection> T findAll(Class modelClass);

	List<Object[]> findHistory(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer firstResult, Integer maxResults, Class modelClass)
			throws Exception;

	int countHistory(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer firstResult, Integer maxResults, Class modelClass) throws Exception;

	<T> Page<T> findPage(Specification specification, Pageable pageable, Class modelClass) throws Exception;

	<T> T saveEntity(Object model, Class modelClass) throws BusinessException;

	<T> T saveEntityQuietly(Object model, Class modelClass) throws BusinessException;

	void deleteEntity(Object entityModel, Class modelClass) throws BusinessException;

	void deleteEntityQuietly(Object entityModel, Class modelClass) throws BusinessException;

	void deleteByUuid(UUID uuid, Class modelClass) throws BusinessException;

	void deleteQuietlyByUuid(UUID uuid, Class modelClass) throws BusinessException;

	<T> T getEntity(Object model, Class modelClass) throws Exception;

	<T> T getEntity(Object model, Class modelClass, EntityConverterContext context) throws Exception;

	<T extends Collection> T getEntity(Collection model, Class modelClass) throws Exception;

	<T extends Collection> T getEntity(Collection model, Class modelClass, EntityConverterContext context) throws Exception;

	<T> T getDto(Object model, Class modelClass) throws Exception;

	<T> T getDto(Object model, Class modelClass, DtoConverterContext context) throws Exception;

	<T extends Collection> T getDto(Collection models, Class modelClass) throws Exception;

	<T extends Collection> T getDto(Collection models, Class modelClass, DtoConverterContext context) throws Exception;

	void initDefaults(Object model, Class modelClass) throws Exception;

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

	<T> T dtoConverter(Object model, DtoConverterContext context, String typeCode) throws ConverterException;

	<T extends Collection> T dtoConverter(Collection models, DtoConverterContext context, String typeCode) throws ConverterException, InterceptorException;

}
