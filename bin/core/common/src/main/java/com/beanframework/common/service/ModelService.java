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

	void attach(Object model);

	void detach(Object model);

	void detachAll();

	<T> T create(Class modelClass) throws Exception;

	<T> T findOneEntityByUuid(UUID uuid, Class modelClass) throws Exception;

	<T> T findOneEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T> T findOneDtoByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findDtoByProperties(Map<String, Object> properties, Class modelClass) throws Exception;

	<T extends Collection> T findDtoBySorts(Map<String, Sort.Direction> sorts, Class modelClass) throws Exception;

	<T extends Collection> T findDtoByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts,
			Class modelClass) throws Exception;

	boolean existsByProperties(Map<String, Object> properties, Class modelClass);

	<T extends Collection> T findAll(Class modelClass) throws Exception;

	<T extends Collection> T findAll(Specification specification, Class modelClass) throws Exception;

	<T> Page<T> findPage(Specification specification, Pageable pageable, Class modelClass) throws Exception;

	void refresh(Object model);

	void saveEntity(Object model) throws BusinessException;

	void saveDto(Object model) throws BusinessException;

	void saveAll() throws BusinessException;

	void remove(Object model) throws BusinessException;

	void remove(Collection<? extends Object> models) throws BusinessException;

	void remove(UUID uuid, Class modelClass) throws BusinessException;

	int removeAll(Class modelClass) throws BusinessException;

	<T> T getEntity(Object model) throws Exception;

	<T extends Collection> T getEntity(Collection<? extends Object> model) throws Exception;

	<T> T getDto(Object model) throws Exception;

	<T extends Collection> T getDto(Collection<? extends Object> models) throws Exception;

	void initDefaults(Object model) throws Exception;

}
