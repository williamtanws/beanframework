package com.beanframework.common.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.common.exception.BusinessException;
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
	public <T> T create(Class modelClass) throws Exception {
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
	public <T> T findOneEntityByUuid(UUID uuid, Class modelClass) throws Exception {
		Assert.notNull(uuid, "uuid was null");
		Assert.notNull(modelClass, "modelClass was null");

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(GenericDomain.UUID, uuid);

		return (T) findOneEntityByProperties(properties, modelClass);
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findOneEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		return findOneByProperties(properties, modelClass, false);
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findOneDtoByProperties(Map<String, Object> properties, Class modelClass) throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		return findOneByProperties(properties, modelClass, true);
	}

	@Transactional(readOnly = true)
	private <T> T findOneByProperties(Map<String, Object> properties, Class modelClass, boolean dto) throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		Object model = getCachedSingleResult(properties, null, null, modelClass);

		if (model == null) {
			try {
				model = createQuery(properties, null, null, modelClass).getSingleResult();

				setCachedSingleResult(properties, null, null, modelClass, model);

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
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		Long count = getCachedSingleResult(properties, null, "count(o)", modelClass);

		if (count == null) {
			count = (Long) createQuery(properties, null, "count(o)", modelClass).getSingleResult();

			setCachedSingleResult(properties, null, "count(o)", modelClass, count);
		}

		return count.equals(0L) ? false : true;
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findDtoByProperties(Map<String, Object> properties, Class modelClass)
			throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		List<Object> models = getCachedResultList(properties, null, null, modelClass);

		if (models == null) {

			models = createQuery(properties, null, null, modelClass).getResultList();

			setCachedResultList(properties, null, null, modelClass, models);
		}

		dtoConverter(models);

		loadInterceptor(models);

		return (T) models;
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findDtoBySorts(Map<String, Sort.Direction> sorts, Class modelClass)
			throws Exception {
		Assert.notNull(sorts, "sorts was null");
		Assert.notNull(modelClass, "modelClass was null");

		List<Object> models = getCachedResultList(null, sorts, null, modelClass);

		if (models == null) {
			models = createQuery(null, sorts, null, modelClass).getResultList();

			setCachedResultList(null, sorts, null, modelClass, models);
		}

		dtoConverter(models);
		loadInterceptor(models);

		return (T) models;
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findDtoByPropertiesAndSorts(Map<String, Object> properties,
			Map<String, Sort.Direction> sorts, Class modelClass) throws Exception {
		if (properties == null && sorts == null) {
			Assert.notNull(properties, "properties was null");
			Assert.notNull(sorts, "sorts was null");
		}
		Assert.notNull(modelClass, "modelClass was null");

		List<Object> models = getCachedResultList(properties, sorts, null, modelClass);

		if (models == null) {
			models = createQuery(properties, sorts, null, modelClass).getResultList();

			setCachedResultList(properties, sorts, null, modelClass, models);
		}

		dtoConverter(models);
		loadInterceptor(models);

		return (T) models;
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findAll(Class modelClass) throws Exception {

		List<Object> models = getCachedResultList(null, null, null, modelClass);

		if (models == null) {
			models = createQuery(null, null, null, modelClass).getResultList();

			setCachedResultList(null, null, null, modelClass, models);
		}

		dtoConverter(models);
		loadInterceptor(models);

		return (T) models;
	}

	@Transactional(readOnly = true)
	@Override
	public <T> Page<T> findPage(Specification spec, Pageable pageable, Class modelClass) throws Exception {
		Page<T> page = (Page<T>) page(spec, pageable, modelClass);

		dtoConverter(page.getContent());
		loadInterceptor(page.getContent());

		List<T> content = getDto(page.getContent());
		PageImpl<T> pageImpl = new PageImpl<T>(content, page.getPageable(), page.getTotalElements());

		return pageImpl;
	}

	@Override
	public void refresh(Object model) {
		entityManager.refresh(model);
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void saveEntity(Object model) throws BusinessException {

		validateInterceptor(model);
		prepareInterceptor(model);

		modelRepository.save(model);

		cacheManager.getCache(model.getClass().getName()).clear();
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void saveDto(Object model) throws BusinessException {

		validateInterceptor(model);
		prepareInterceptor(model);

		model = entityConverter(model);

		modelRepository.save(model);

		cacheManager.getCache(model.getClass().getName()).clear();

		dtoConverter(model);
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void saveAll() throws BusinessException {
		modelRepository.flush();
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void remove(UUID uuid, Class modelClass) throws BusinessException {

		Object model;
		try {
			model = findOneEntityByUuid(uuid, modelClass);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

		Query query = entityManager.createQuery("delete from " + modelClass.getName() + " o where o.uuid = :uuid");
		query.setParameter("uuid", uuid);
		try {
			int count = query.executeUpdate();

			if (count == 0) {
				throw new BusinessException("UUID not exists.");
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

		cacheManager.getCache(modelClass.getName()).clear();

		removeInterceptor(model);
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public int removeAll(Class modelClass) throws BusinessException {
		Query query = entityManager.createQuery("delete from " + modelClass.getName());
		int count = query.executeUpdate();

		cacheManager.getCache(modelClass.getName()).clear();

		return count;
	}

	@Override
	public <T> T getEntity(Object model) throws Exception {
		model = entityConverter(model);
		return (T) model;
	}

	@Override
	public <T extends Collection> T getEntity(Collection<? extends Object> models) throws Exception {
		List<Object> entityObjects = new ArrayList<Object>();
		for (Object model : models) {
			entityObjects.add(entityConverter(model));
		}
		return (T) entityObjects;
	}

	@Override
	public <T> T getDto(Object model) throws Exception {
		dtoConverter(model);
		return (T) model;
	}

	@Override
	public <T extends Collection> T getDto(Collection<? extends Object> models) throws Exception {
		List<Object> dtoObjects = new ArrayList<Object>();
		for (Object model : models) {
			dtoObjects.add(dtoConverter(model));
		}
		return (T) dtoObjects;
	}

	@Override
	public void initDefaults(Object model) throws Exception {
		initialDefaultsInterceptor(model);
	}
}
