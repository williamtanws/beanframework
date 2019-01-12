package com.beanframework.common.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
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
	public void attach(Object model, Class modelClass) {
		entityManager.merge(model);
	}

	@Transactional
	@Override
	public void detach(Object model, Class modelClass) {
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

		try {
			Object model = modelClass.newInstance();
			initialDefaultsInterceptor(model, modelClass);
			return (T) model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findOneEntityByUuid(UUID uuid, Class modelClass) throws Exception {
		Assert.notNull(uuid, "uuid was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {

			Object model = entityManager.find(modelClass, uuid);
			loadInterceptor(model, modelClass);
			return (T) model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findOneDtoByUuid(UUID uuid, Class modelClass) throws Exception {
		Assert.notNull(uuid, "uuid was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(GenericDomain.UUID, uuid);

			Object model = (T) findOneEntityByProperties(properties, modelClass);
			loadInterceptor(model, modelClass);

			model = getDto(model, modelClass);

			return (T) model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findOneEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {
			return (T) findOneByProperties(properties, modelClass, false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findOneDtoByProperties(Map<String, Object> properties, Class modelClass) throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {
			return findOneByProperties(properties, modelClass, true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	private <T> T findOneByProperties(Map<String, Object> properties, Class modelClass, boolean dto) throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {
			Object model = getCachedSingleResult(properties, null, null, modelClass);

			if (model == null) {
				try {
					model = createQuery(properties, null, null, null, null, modelClass).getSingleResult();

					setCachedSingleResult(properties, null, null, modelClass, model);

				} catch (NoResultException e) {
					return null;
				}
			}

			if (model != null) {
				loadInterceptor(model, modelClass);
				if (dto) {
					model = getDto(model, modelClass);
				}
			}

			return (T) model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public boolean existsByProperties(Map<String, Object> properties, Class modelClass) throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {
			Long count = getCachedSingleResult(properties, null, "count(o)", modelClass);

			if (count == null) {
				count = (Long) createQuery(properties, null, "count(o)", null, null, modelClass).getSingleResult();

				setCachedSingleResult(properties, null, "count(o)", modelClass, count);
			}

			return count.equals(0L) ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findEntityByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Integer firstResult, Integer maxResult, Class modelClass)
			throws Exception {
		Assert.notNull(modelClass, "modelClass was null");

		try {
			return (T) createQuery(properties, sorts, null, firstResult, maxResult, modelClass).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findDtoByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Integer firstResult, Integer maxResult, Class modelClass)
			throws Exception {
		Assert.notNull(modelClass, "modelClass was null");

		try {
			List<Object> models = getCachedResultList(properties, sorts, null, firstResult, maxResult, modelClass);

			if (models == null) {
				models = createQuery(properties, sorts, null, firstResult, maxResult, modelClass).getResultList();

				setCachedResultList(properties, sorts, null, maxResult, modelClass, models);
			}

			if (models == null || models.isEmpty()) {
				return (T) new ArrayList<T>();
			}

			loadInterceptor(models, modelClass);

			return (T) getDto(models, modelClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findAllEntity(Class modelClass) throws Exception {
		return findEntityByPropertiesAndSorts(null, null, null, null, modelClass);

	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findAllDto(Class modelClass) throws Exception {
		return findDtoByPropertiesAndSorts(null, null, null, null, modelClass);
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findHistory(boolean selectDeletedEntities, AuditCriterion criterion, AuditOrder order, Integer firstResult, Integer maxResults, Class modelClass) throws Exception {

		// Create the Audit Reader. It uses the EntityManager, which will be opened when
		// starting the new Transation and closed when the Transaction finishes.
		AuditReader auditReader = AuditReaderFactory.get(entityManager);

		// Create the Query:
		AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntityWithChanges(modelClass, selectDeletedEntities);

		if (criterion != null)
			auditQuery.add(criterion);

		if (order != null)
			auditQuery.addOrder(order);

		if (firstResult != null)
			auditQuery.setFirstResult(firstResult);

		if (maxResults != null)
			auditQuery.setMaxResults(maxResults);

		return (T) auditQuery.getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public <T> Page<T> findDtoPage(Specification spec, Pageable pageable, Class modelClass) throws Exception {
		try {
			Page<T> page = (Page<T>) page(spec, pageable, modelClass);

			Iterator<T> i = page.getContent().iterator();
			while (i.hasNext()) {
				loadInterceptor(i.next(), modelClass);
			}

			List<T> content = getDto(page.getContent(), modelClass);
			PageImpl<T> pageImpl = new PageImpl<T>(content, page.getPageable(), page.getTotalElements());

			return pageImpl;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Override
	public void refresh(Object model) {
		entityManager.refresh(model);
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public Object saveEntity(Object model, Class modelClass) throws BusinessException {

		try {
			prepareInterceptor(model, modelClass);
			validateInterceptor(model, modelClass);
			modelRepository.save(model);

			clearCache(modelClass);

			return model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public Object saveDto(Object model, Class modelClass) throws BusinessException {

		try {
			model = entityConverter(model, modelClass);
			prepareInterceptor(model, modelClass);
			validateInterceptor(model, modelClass);
			modelRepository.save(model);

			clearCache(modelClass);

			return model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void flush() throws BusinessException {
		modelRepository.flush();
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void deleteByEntity(Object entityModel, Class modelClass) throws BusinessException {
		try {

			deleteEntity(entityModel);

			removeInterceptor(entityModel, modelClass);

			clearCache(modelClass);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void deleteByUuid(UUID uuid, Class modelClass) throws BusinessException {
		try {

			Object model = findOneEntityByUuid(uuid, modelClass);

			deleteEntity(model);

			removeInterceptor(model, modelClass);

			clearCache(modelClass);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void deleteAll(Class modelClass) throws BusinessException {
		try {
			List<Object> models = findEntityByPropertiesAndSorts(null, null, null, null, modelClass);
			for (Object model : models) {
				deleteEntity(model);
			}

			clearCache(modelClass);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = SQLException.class)
	private void deleteEntity(Object model) throws SQLException {
		modelRepository.delete(model);
	}

	@Override
	public <T> T getEntity(Object model, Class modelClass) throws Exception {
		try {
			model = entityConverter(model, modelClass);
			return (T) model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Override
	public <T extends Collection> T getEntity(Collection models, Class modelClass) throws Exception {
		try {
			List<Object> entityObjects = new ArrayList<Object>();
			for (Object model : models) {
				model = entityConverter(model, modelClass);
				entityObjects.add(model);
			}
			return (T) entityObjects;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Override
	public <T> T getDto(Object model, Class modelClass) throws Exception {
		try {
			if (model == null)
				return null;
			model = dtoConverter(model, modelClass);
			return (T) model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Override
	public <T extends Collection> T getDto(Collection models, Class modelClass) throws Exception {
		try {
			if (models == null)
				return null;
			if (models.isEmpty()) {
				return (T) models;
			}

			return (T) dtoConverter(models, modelClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Override
	public void initDefaults(Object model, Class modelClass) throws Exception {
		initialDefaultsInterceptor(model, modelClass);
	}

	@Override
	public void clearCache(Class modelClass) {
		cacheManager.getCache(modelClass.getName()).clear();
	}
}
