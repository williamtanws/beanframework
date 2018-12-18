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
import org.springframework.cache.CacheManager;
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

	@Autowired
	private CacheManager cacheManager;

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
	public <T> T findOneEntityByUuid(UUID uuid, Class modelClass) {
		Assert.notNull(uuid, "uuid was null");
		Assert.notNull(modelClass, "modelClass was null");

		Object model = cacheManager.getCache(modelClass.getName()).get(uuid);

		if (model == null) {
			model = (T) entityManager.getReference(modelClass, uuid);
			cacheManager.getCache(modelClass.getName()).put(uuid, model);
		}

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
				propertiesBuilder.append("o." + entry.getKey() + " = " + entry.getValue());
			} else {
				propertiesBuilder.append(" and o." + entry.getKey() + " = " + entry.getValue());
			}
		}

		String qlString = "select o from " + modelClass.getName() + " o where " + propertiesBuilder.toString();

		Object model = cacheManager.getCache(modelClass.getName()).get(qlString);

		if (model == null) {
			Query query = entityManager.createQuery(qlString);

			try {
				model = query.getSingleResult();

				cacheManager.getCache(modelClass.getName()).put(qlString, model);

				if (dto) {
					dtoConverter(model);
				}
				loadInterceptor(model);

				return (T) model;
			} catch (NoResultException e) {
				return null;
			}
		}

		if (dto) {
			dtoConverter(model);
		}
		loadInterceptor(model);

		return (T) model;
	}

	@Transactional(readOnly = true)
	@Override
	public boolean existsByProperties(Map<String, Object> properties, Class modelClass) {
		StringBuilder propertiesBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (propertiesBuilder.length() == 0) {
				propertiesBuilder.append("o." + entry.getKey() + " = " + entry.getValue());
			} else {
				propertiesBuilder.append(" and o." + entry.getKey() + " = " + entry.getValue());
			}
		}

		String qlString = "select count(o) from " + modelClass.getName() + " o where " + propertiesBuilder.toString();

		Long count = cacheManager.getCache(modelClass.getName()).get(qlString, Long.class);

		if (count == null) {
			Query query = entityManager.createQuery(qlString);

			count = (Long) query.getSingleResult();

			cacheManager.getCache(modelClass.getName()).put(qlString, count);
		}

		return count.equals(0L) ? false : true;
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findByProperties(Map<String, Object> properties, Class modelClass) {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		StringBuilder propertiesBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {

			if (propertiesBuilder.length() == 0) {
				propertiesBuilder.append("o." + entry.getKey() + " = " + entry.getValue());
			} else {
				propertiesBuilder.append(" and o." + entry.getKey() + " = " + entry.getValue());
			}
		}

		String qlString = "select o from " + modelClass.getName() + " o where " + propertiesBuilder.toString();

		List<Object> models = (List<Object>) cacheManager.getCache(modelClass.getName()).get(qlString);

		if (models == null) {
			Query query = entityManager.createQuery(qlString);

			models = query.getResultList();

			cacheManager.getCache(modelClass.getName()).put(qlString, models);
		}

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

		String qlString = "select o from " + modelClass.getName() + " o " + sortsBuilder.toString();

		List<Object> models = (List<Object>) cacheManager.getCache(modelClass.getName()).get(qlString);

		if (models == null) {
			Query query = entityManager.createQuery(qlString);

			models = query.getResultList();

			cacheManager.getCache(modelClass.getName()).put(qlString, models);
		}

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
				if (entry.getValue() == null) {
					propertiesBuilder.append("o." + entry.getKey() + " IS NULL");
				} else {
					propertiesBuilder.append("o." + entry.getKey() + " = " + entry.getValue());
				}
			} else {
				propertiesBuilder.append(" and o." + entry.getKey() + " = " + entry.getValue());
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
		
		String qlString = "select o from " + modelClass.getName() + " o where "+ propertiesBuilder.toString() + " " + sortsBuilder.toString();
		
		List<Object> models = (List<Object>) cacheManager.getCache(modelClass.getName()).get(qlString);
		
		if(models == null) {
			Query query = entityManager.createQuery(qlString);

			models = query.getResultList();
			
			cacheManager.getCache(modelClass.getName()).put(qlString, models);
		}

		dtoConverter(models);
		loadInterceptor(models);

		return (T) models;
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findAll(Class modelClass) {
		
		List<Object> models = (List<Object>) cacheManager.getCache(modelClass.getName()).get(modelClass.getName());
		
		if(models == null) {
			models = (List<Object>) modelRepository.findAll();
			
			cacheManager.getCache(modelClass.getName()).put(modelClass.getName(), models);
		}

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
	public void saveEntity(Object model) throws ModelSavingException {
				
		validateInterceptor(model);
		prepareInterceptor(model);

		modelRepository.save(model);
		
		cacheManager.getCache(model.getClass().getName()).clear();
	}

	@Transactional
	@Override
	public void saveDto(Object model) throws ModelSavingException {

		validateInterceptor(model);
		prepareInterceptor(model);

		model = entityConverter(model);

		modelRepository.save(model);
		
		cacheManager.getCache(model.getClass().getName()).clear();

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
		
		cacheManager.getCache(model.getClass().getName()).clear();
		
		removeInterceptor(model);
	}

	@Transactional
	@Override
	public void remove(Collection<? extends Object> models) throws ModelRemovalException {
		for (Object model : models) {
			modelRepository.delete(model);
			
			cacheManager.getCache(model.getClass().getName()).clear();
			
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
		
		cacheManager.getCache(model.getClass().getName()).clear();
		
		removeInterceptor(model);
	}

	@Transactional
	@Override
	public int removeAll(Class modelClass) throws ModelRemovalException {
		Query query = entityManager.createQuery("delete from " + modelClass.getName());
		int count = query.executeUpdate();
		
		cacheManager.getCache(modelClass.getName()).clear();
		
		return count;
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
