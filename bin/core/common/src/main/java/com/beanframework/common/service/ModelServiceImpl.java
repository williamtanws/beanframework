package com.beanframework.common.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.beanframework.common.exception.ModelInitializationException;
import com.beanframework.common.exception.ModelRemovalException;
import com.beanframework.common.exception.ModelSavingException;
import com.beanframework.common.repository.ModelRepository;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class ModelServiceImpl extends AbstractModelServiceImpl {

	@Autowired
	private ModelRepository modelRepository;

	@Transactional
	@Override
	public void attach(Object model) {
		entityManager.merge(model);
	}

	@Transactional
	@Override
	public void detach(Object model) {
		entityManager.detach(model);
	}

	@Transactional
	@Override
	public void detachAll() {
		entityManager.clear();
	}

	@Override
	public <T> T create(Class modelClass) {
		Assert.notNull(modelClass, "modelClass was null");

		Object model = null;
		try {
			model = modelClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialDefaultsInterceptor(model);
		return (T) model;
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findOneEntityByProperties(Map<String, Object> properties, Class modelClass) {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		return findOneByProperties(properties, modelClass, false);
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findOneDtoByProperties(Map<String, Object> properties, Class modelClass) {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		return findOneByProperties(properties, modelClass, true);
	}

	@Transactional(readOnly = true)
	private <T> T findOneByProperties(Map<String, Object> properties, Class modelClass, boolean dto) {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		StringBuilder propertiesBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (propertiesBuilder.length() == 0) {
				propertiesBuilder.append("o." + entry.getKey() + " = :" + entry.getKey());
			} else {
				propertiesBuilder.append(" and o." + entry.getKey() + " = :" + entry.getKey());
			}
		}

		Query query = entityManager
				.createQuery("select o from " + modelClass.getName() + " o where " + propertiesBuilder.toString());

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		Object model;
		try {
			model = query.getSingleResult();

			if (dto) {
				dtoConverter(model);
			}
			loadInterceptor(model);

			return (T) model;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public boolean existsByProperties(Map<String, Object> properties, Class modelClass) {
		StringBuilder propertiesBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (propertiesBuilder.length() == 0) {
				propertiesBuilder.append("o." + entry.getKey() + " = :" + entry.getKey());
			} else {
				propertiesBuilder.append(" and o." + entry.getKey() + " = :" + entry.getKey());
			}
		}

		Query query = entityManager.createQuery(
				"select count(o) from " + modelClass.getName() + " o where " + propertiesBuilder.toString());

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		Collection models = query.getResultList();

		if (models.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findByProperties(Map<String, Object> properties, Class modelClass) {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		StringBuilder propertiesBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {

			if (propertiesBuilder.length() == 0) {
				propertiesBuilder.append("o." + entry.getKey() + " = :" + entry.getKey());
			} else {
				propertiesBuilder.append(" and o." + entry.getKey() + " = :" + entry.getKey());
			}
		}

		Query query = entityManager
				.createQuery("select o from " + modelClass.getName() + " o where " + propertiesBuilder.toString());

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		Collection models = query.getResultList();

		dtoConverter(models);

		loadInterceptor(models);

		return (T) models;
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findBySorts(Map<String, Sort.Direction> sorts, Class modelClass) {
		Assert.notNull(sorts, "sorts was null");
		Assert.notNull(modelClass, "modelClass was null");

		StringBuilder sortsBuilder = new StringBuilder();
		for (Entry<String, Direction> entry : sorts.entrySet()) {
			if (sortsBuilder.length() == 0) {
				sortsBuilder.append("order by o." + entry.getKey() + " " + entry.getValue().toString());
			} else {
				sortsBuilder.append(", o." + entry.getKey() + " " + entry.getValue().toString());
			}
		}

		Query query = entityManager
				.createQuery("select o from " + modelClass.getName() + " o " + sortsBuilder.toString());

		Collection models = query.getResultList();

		dtoConverter(models);
		loadInterceptor(models);

		return (T) models;
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findByPropertiesAndSorts(Map<String, Object> properties,
			Map<String, Sort.Direction> sorts, Class modelClass) {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(sorts, "sorts was null");
		Assert.notNull(modelClass, "modelClass was null");

		StringBuilder propertiesBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (propertiesBuilder.length() == 0) {
				propertiesBuilder.append("o." + entry.getKey() + " = :" + entry.getKey());
			} else {
				propertiesBuilder.append(" and o." + entry.getKey() + " = :" + entry.getKey());
			}
		}

		StringBuilder sortsBuilder = new StringBuilder();
		for (Entry<String, Direction> entry : sorts.entrySet()) {
			if (sortsBuilder.length() == 0) {
				sortsBuilder.append("order by o." + entry.getKey() + " " + entry.getValue().toString());
			} else {
				sortsBuilder.append(", o." + entry.getKey() + " " + entry.getValue().toString());
			}
		}

		Query query = entityManager.createQuery("select o from " + modelClass.getName() + " o where "
				+ propertiesBuilder.toString() + " " + sortsBuilder.toString());

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		Collection models = query.getResultList();

		dtoConverter(models);
		loadInterceptor(models);

		return (T) models;
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findAll() {
		Collection models = (T) modelRepository.findAll();

		dtoConverter(models);
		loadInterceptor(models);

		return (T) models;
	}

	@Transactional(readOnly = true)
	@Override
	public <T> Page<T> findPage(@Nullable Specification spec, Pageable pageable, Class modelClass) {
		Page<T> page = (Page<T>) page(spec, pageable, modelClass);

		dtoConverter(page.getContent());
		loadInterceptor(page.getContent());

		return page;
	}

	@Override
	public void refresh(Object model) {
		entityManager.refresh(model);
	}

	@Transactional
	@Override
	public void save(Object model) throws ModelSavingException {

		validateInterceptor(model);
		prepareInterceptor(model);

		model = entityConverter(model);

		modelRepository.save(model);

		dtoConverter(model);
	}

	@Transactional
	@Override
	public void saveAll() throws ModelSavingException {
		modelRepository.flush();
	}

	@Transactional
	@Override
	public void remove(Object model) throws ModelRemovalException {
		modelRepository.delete(model);
		removeInterceptor(model);
	}

	@Transactional
	@Override
	public void remove(Collection<? extends Object> models) throws ModelRemovalException {
		for (Object model : models) {
			modelRepository.delete(model);
			removeInterceptor(model);
		}
	}

	@Transactional
	@Override
	public void remove(UUID uuid, Class modelClass) throws ModelRemovalException {
		Object model = entityManager.find(modelClass, uuid);

		if (model == null) {
			throw new ModelRemovalException("UUID not exists.", new Exception());
		}

		modelRepository.delete(model);
		removeInterceptor(model);
	}

	@Transactional
	@Override
	public int removeAll(Class modelClass) throws ModelRemovalException {
		Query query = entityManager.createQuery("delete from " + modelClass.getName());
		return query.executeUpdate();
	}

	@Override
	public <T> T getEntity(Object model) {
		model = entityConverter(model);
		return (T) model;
	}

	@Override
	public <T extends Collection> T getEntity(Collection<? extends Object> models) {
		List<Object> entityObjects = new ArrayList<Object>();
		for (Object model : models) {
			entityObjects.add(entityConverter(model));
		}
		return (T) entityObjects;
	}

	@Override
	public <T> T getDto(Object model) {
		dtoConverter(model);
		return (T) model;
	}

	@Override
	public <T extends Collection> T getDto(Collection<? extends Object> models) {
		List<Object> dtoObjects = new ArrayList<Object>();
		for (Object model : models) {
			dtoObjects.add(dtoConverter(model));
		}
		return (T) dtoObjects;
	}

	@Override
	public void initDefaults(Object model) throws ModelInitializationException {
		initialDefaultsInterceptor(model);
	}
}
