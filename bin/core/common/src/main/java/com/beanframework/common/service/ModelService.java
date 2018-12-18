package com.beanframework.common.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.ModelInitializationException;
import com.beanframework.common.exception.ModelRemovalException;
import com.beanframework.common.exception.ModelSavingException;

@SuppressWarnings("rawtypes")
public interface ModelService {

	void attach(Object model);

	void detach(Object model);

	void detachAll();

	<T> T create(Class modelClass);

	<T> T findOneEntityByUuid(UUID uuid, Class modelClass);

	<T> T findOneEntityByProperties(Map<String, Object> properties, Class modelClass);

	<T> T findOneDtoByProperties(Map<String, Object> properties, Class modelClass);

	<T extends Collection> T findDtoByProperties(Map<String, Object> properties, Class modelClass);

	<T extends Collection> T findDtoBySorts(Map<String, Sort.Direction> sorts, Class modelClass);

	<T extends Collection> T findDtoByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts,
			Class modelClass);

	boolean existsByProperties(Map<String, Object> properties, Class modelClass);

	<T extends Collection> T findAll(Class modelClass);

	<T extends Collection> T findAll(Specification specification, Class modelClass);

	<T> Page<T> findPage(Specification specification, Pageable pageable, Class modelClass);

	void refresh(Object model);

	void saveEntity(Object model) throws ModelSavingException;

	void saveDto(Object model) throws ModelSavingException;

	void saveAll() throws ModelSavingException;

	void remove(Object model) throws ModelRemovalException;

	void remove(Collection<? extends Object> models) throws ModelRemovalException;

	void remove(UUID uuid, Class modelClass) throws ModelRemovalException;

	int removeAll(Class modelClass) throws ModelRemovalException;

	<T> T getEntity(Object model);

	<T extends Collection> T getEntity(Collection<? extends Object> model);

	<T> T getDto(Object model);

	<T extends Collection> T getDto(Collection<? extends Object> models);

	void initDefaults(Object model) throws ModelInitializationException;

}
