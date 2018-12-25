package com.beanframework.common.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

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

	<T> T findOneEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T> T findOneDtoByProperties(Map<String, Object> properties, Class modelClass) throws Exception;
	
	<T extends Collection> T findEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findEntityBySorts(Map<String, Sort.Direction> sorts, Class modelClass) throws Exception;
	
	<T extends Collection> T findEntityByPropertiesAndSorts(Map<String, Object> properties,
			Map<String, Sort.Direction> sorts, Class modelClass) throws Exception;

	<T extends Collection> T findDtoByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findDtoBySorts(Map<String, Sort.Direction> sorts, Class modelClass) throws Exception;

	<T extends Collection> T findDtoByPropertiesAndSorts(Map<String, Object> properties,
			Map<String, Sort.Direction> sorts, Class modelClass) throws Exception;

	boolean existsByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findAll(Class modelClass) throws Exception;

	<T> Page<T> findPage(Specification specification, Pageable pageable, Class modelClass) throws Exception;

	void refresh(Object model);

	void saveEntity(Object model, Class modelClass) throws BusinessException;

	void saveDto(Object model, Class modelClass) throws BusinessException;

	void saveAll() throws BusinessException;

	void delete(UUID uuid, Class modelClass) throws BusinessException;

	void deleteAll(Class modelClass) throws BusinessException;

	<T> T getEntity(Object model, Class modelClass) throws Exception;

	<T extends Collection> T getEntity(Collection model, Class modelClass) throws Exception;

	<T> T getDto(Object model, Class modelClass) throws Exception;

	<T extends Collection> T getDto(Collection models, Class modelClass) throws Exception;

	void initDefaults(Object model, Class modelClass) throws Exception;

	public void clearCache(Class modelClass);
}
