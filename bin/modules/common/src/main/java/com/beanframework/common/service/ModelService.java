package com.beanframework.common.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Query;

import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.specification.AbstractSpecification;

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

	<T> T findOneByUuid(UUID uuid, Class modelClass, String typeCode) throws Exception;

	<T> T findOneByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	int countAll(Class modelClass) throws Exception;

	int countByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	int countBySpecification(AbstractSpecification specification, Class modelClass) throws Exception;

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
	<T extends Collection> T findByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findByPropertiesBySortByResult(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Integer firstResult, Integer maxResult, Class modelClass) throws Exception;

	<T extends Collection> T findBySpecificationBySort(AbstractSpecification specification, Sort sort, Class modelClass) throws Exception;

	<T extends Collection> T findBySpecification(AbstractSpecification specification, Class modelClass) throws Exception;

	<T extends Collection> T findAll(Class modelClass);

	List<?> searchByQuery(String qlString);
	
	Query createNativeQuery(String sqlString);

	List<Object[]> findHistory(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer firstResult, Integer maxResults, Class modelClass) throws Exception;

	int countHistory(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Class modelClass) throws Exception;

	<T> Page<T> findPage(AbstractSpecification specification, Pageable pageable, Class modelClass) throws Exception;

	<T> T saveEntity(Object model) throws BusinessException;

	/**
	 * Quietly means it skip listener events, mostly used by listener itself.
	 * 
	 * @param model
	 * @param modelClass
	 * @return
	 * @throws BusinessException
	 */
	<T> T saveEntityByLegacyMode(Object model, Class modelClass) throws BusinessException;

	void deleteEntity(Object entityModel, Class modelClass) throws BusinessException;

	/**
	 * Quietly means it skip listener events, mostly used by listener itself.
	 * 
	 * @param model
	 * @param modelClass
	 * @return
	 * @throws BusinessException
	 */
	void deleteEntityByLegacyMode(Object entityModel, Class modelClass) throws BusinessException;

	void deleteByUuid(UUID uuid, Class modelClass) throws BusinessException;

	void deleteAll(Class modelClass) throws BusinessException;

	/**
	 * Quietly means it skip listener events, mostly used by listener itself.
	 * 
	 * @param model
	 * @param modelClass
	 * @return
	 * @throws BusinessException
	 */
	void deleteEntityByLegacyModeByUuid(UUID uuid, Class modelClass) throws BusinessException;

	<T> T getEntity(Object model, Class modelClas) throws Exception;

	<T> T getEntity(Object model, String typeCode) throws Exception;

	<T extends Collection> T getEntityList(Collection model, Class modelClass) throws Exception;

	<T extends Collection> T getEntityList(Collection model, String typeCode) throws Exception;

	<T> T getDto(Object model, Class modelClass) throws Exception;

	<T> T getDto(Object model, String typeCode) throws Exception;

	<T extends Collection> T getDtoList(Collection models, Class modelClass) throws Exception;

	<T extends Collection> T getDtoList(Collection models, String typeCode) throws Exception;

	void initDefaults(Object model, Class modelClass) throws Exception;

	void initialDefaultsInterceptor(Collection models, String typeCode) throws InterceptorException;

	void initialDefaultsInterceptor(Object model, String typeCode) throws InterceptorException;

	void loadInterceptor(Collection models, String typeCode) throws InterceptorException;

	void loadInterceptor(Object model, String typeCode) throws InterceptorException;

	void prepareInterceptor(Collection models, String typeCode) throws InterceptorException;

	void prepareInterceptor(Object model, String typeCode) throws InterceptorException;

	void removeInterceptor(Collection models, String typeCode) throws InterceptorException;

	void removeInterceptor(Object model, String typeCode) throws InterceptorException;

	void validateInterceptor(Collection models, String typeCode) throws InterceptorException;

	void validateInterceptor(Object model, String typeCode) throws InterceptorException;

	void entityConverter(Collection models, Class modelClass) throws ConverterException;

	<T> T entityConverter(Object model, String typeCode) throws ConverterException;

	<T> T dtoConverter(Object model, String typeCode) throws ConverterException;

	<T extends Collection> T dtoConverter(Collection models, String typeCode) throws ConverterException, InterceptorException;

}
