package com.beanframework.common.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.beanframework.common.exception.ModelInitializationException;
import com.beanframework.common.exception.ModelRemovalException;
import com.beanframework.common.exception.ModelSavingException;

@SuppressWarnings("rawtypes")
public interface ModelService {

	void attach(Object object);

	void detach(Object object);

	void detachAll();

	<T> T create(Class objectClass);

	<T> T findOne(Map<String, String> parameters, Class objectClass);

	<T extends Collection> T find(Map<String, String> parameters, Class objectClass);

	<T> T findByUuid(UUID uuid, Class objectClass);

	<T extends Collection> T findAll();

	<T extends Collection> T findAll(@Nullable Specification spec, Pageable pageable, Class objectClass);
	
	void refresh(Object object);

	void save(Object object) throws ModelSavingException;

	void saveAll() throws ModelSavingException;

	void remove(Object object) throws ModelRemovalException;

	void remove(UUID uuid, Class objectClass) throws ModelRemovalException;
	
	void removeAll() throws ModelRemovalException;

	<T> T getEntity(Object object);

	<T> T getDto(Object object);

	void initDefaults(Object object) throws ModelInitializationException;
}
