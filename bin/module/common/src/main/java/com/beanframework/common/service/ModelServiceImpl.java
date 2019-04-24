package com.beanframework.common.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
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

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.registry.AfterRemoveEvent;
import com.beanframework.common.registry.AfterRemoveListener;
import com.beanframework.common.registry.AfterRemoveListenerRegistry;
import com.beanframework.common.registry.AfterSaveEvent;
import com.beanframework.common.registry.AfterSaveListener;
import com.beanframework.common.registry.AfterSaveListenerRegistry;
import com.beanframework.common.repository.ModelRepository;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class ModelServiceImpl extends AbstractModelServiceImpl {

	@Autowired
	private ModelRepository modelRepository;

	@Autowired
	private AfterSaveListenerRegistry afterSaveListenerRegistry;

	@Autowired
	private AfterRemoveListenerRegistry afterRemoveListenerRegistry;

	@Autowired
	private DtoConverterContext dtoConveterContext;

	@Autowired
	private EntityConverterContext entityConveterContext;

	@Autowired
	private InterceptorContext interceptorContext;

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
	
	@Transactional
	@Override
	public <T> T merge(Object model) {
		return (T) entityManager.merge(model);
	}

	@Transactional
	@Override
	public void refresh(Object model) {
		entityManager.refresh(model);
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void flush() throws BusinessException {
		modelRepository.flush();
	}

	@Override
	public <T> T create(Class modelClass) throws Exception {
		Assert.notNull(modelClass, "modelClass was null");

		try {
			Object model = modelClass.newInstance();
			initialDefaultsInterceptor(model, interceptorContext, modelClass.getSimpleName());
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

			if (model != null)
				model = loadInterceptor(model, interceptorContext, modelClass.getSimpleName());

			return (T) model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findOneEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception {
		Assert.notNull(modelClass, "modelClass was null");

		try {
			Object model = createQuery(properties, null, null, null, null, modelClass).getSingleResult();

			if (model != null)
				model = loadInterceptor(model, interceptorContext, modelClass.getSimpleName());

			return (T) model;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public int count(Class modelClass) throws Exception {
		try {
			Long count = (Long) createQuery(null, null, "count(o)", null, null, modelClass).getSingleResult();

			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public int count(Map<String, Object> properties, Class modelClass) throws Exception {
		try {
			Long count = (Long) createQuery(properties, null, "count(o)", null, null, modelClass).getSingleResult();

			return count.intValue();
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
			Long count = (Long) createQuery(properties, null, "count(o)", null, null, modelClass).getSingleResult();

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
			List<Object> models = createQuery(properties, sorts, null, firstResult, maxResult, modelClass).getResultList();

			if (models != null) {
				return (T) loadInterceptor(models, interceptorContext, modelClass.getSimpleName());
			}

			return (T) models;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Object[]> findHistories(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer firstResult, Integer maxResults, Class modelClass)
			throws Exception {

		// Create the Audit Reader. It uses the EntityManager, which will be opened when
		// starting the new Transation and closed when the Transaction finishes.
		AuditReader auditReader = AuditReaderFactory.get(entityManager);

		// Create the Query:
		AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntityWithChanges(modelClass, selectDeletedEntities);

		if (auditCriterions != null) {
			for (AuditCriterion auditCriterion : auditCriterions) {
				auditQuery.add(auditCriterion);
			}
		}

		if (auditOrders != null) {
			for (AuditOrder auditOrder : auditOrders) {
				auditQuery.addOrder(auditOrder);
			}
		}

		if (firstResult != null)
			auditQuery.setFirstResult(firstResult);

		if (maxResults != null)
			auditQuery.setMaxResults(maxResults);

		List<Object[]> result = auditQuery.getResultList();
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public int findCountHistory(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer firstResult, Integer maxResults, Class modelClass)
			throws Exception {

		// Create the Audit Reader. It uses the EntityManager, which will be opened when
		// starting the new Transation and closed when the Transaction finishes.
		AuditReader auditReader = AuditReaderFactory.get(entityManager);

		// Create the Query:
		AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntityWithChanges(modelClass, selectDeletedEntities).addProjection(AuditEntity.id().count());

		if (auditCriterions != null) {
			for (AuditCriterion auditCriterion : auditCriterions) {
				auditQuery.add(auditCriterion);
			}
		}

		if (auditOrders != null) {
			for (AuditOrder auditOrder : auditOrders) {
				auditQuery.addOrder(auditOrder);
			}
		}

		if (firstResult != null)
			auditQuery.setFirstResult(firstResult);

		if (maxResults != null)
			auditQuery.setMaxResults(maxResults);

		Long count = (Long) auditQuery.getSingleResult();
		return count.intValue();
	}

	@Transactional(readOnly = true)
	@Override
	public <T> Page<T> findEntityPage(Specification spec, Pageable pageable, Class modelClass) throws Exception {
		try {
			Page<T> page = (Page<T>) page(spec, pageable, modelClass);

			List<T> content = (List<T>) loadInterceptor(page.getContent(), interceptorContext, modelClass.getSimpleName() + DEFAULT_LIST_LOAD_INTERCEPTOR_POSTFIX);

			PageImpl<T> pageImpl = new PageImpl<T>(content, page.getPageable(), page.getTotalElements());

			return pageImpl;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public Object saveEntity(Object model, Class modelClass) throws BusinessException {

		try {
			AfterSaveEvent event;
			if (((GenericEntity) model).getUuid() == null) {
				event = new AfterSaveEvent(1);
			} else {
				event = new AfterSaveEvent(2);
			}

			prepareInterceptor(model, interceptorContext, modelClass.getSimpleName());
			validateInterceptor(model, interceptorContext, modelClass.getSimpleName());
			modelRepository.save(model);

			Set<Entry<String, AfterSaveListener>> afterSaveListeners = afterSaveListenerRegistry.getListeners().entrySet();
			for (Entry<String, AfterSaveListener> entry : afterSaveListeners) {
				entry.getValue().afterSave(model, event);
			}

			return model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void deleteByEntity(Object entityModel, Class modelClass) throws BusinessException {
		try {

			deleteEntity(entityModel, modelClass);

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

			deleteEntity(model, modelClass);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = SQLException.class)
	private void deleteEntity(Object model, Class modelClass) throws SQLException, InterceptorException, BusinessException {
		removeInterceptor(model, interceptorContext, modelClass.getSimpleName());

		modelRepository.delete(model);

		AfterRemoveEvent event = new AfterRemoveEvent();

		Set<Entry<String, AfterRemoveListener>> afterRemoveListeners = afterRemoveListenerRegistry.getListeners().entrySet();
		for (Entry<String, AfterRemoveListener> entry : afterRemoveListeners) {
			entry.getValue().afterRemove(model, event);
		}
	}

	@Override
	public <T> T getEntity(Object model, Class modelClass) throws Exception {
		try {

			if (model == null) {
				return null;
			}

			model = entityConverter(model, entityConveterContext, modelClass);
			return (T) model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Override
	public <T extends Collection> T getEntity(Collection models, Class modelClass) throws Exception {
		try {

			if (models == null)
				return null;

			if (models.isEmpty())
				return (T) new ArrayList<T>();

			List<Object> entityObjects = new ArrayList<Object>();
			for (Object model : models) {
				model = entityConverter(model, entityConveterContext, modelClass);
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

			model = dtoConverter(model, dtoConveterContext, modelClass.getSimpleName());
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

			if (models.isEmpty())
				return (T) new ArrayList<T>();

			return (T) dtoConverter(models, dtoConveterContext, modelClass.getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Override
	public void initDefaults(Object model, Class modelClass) throws Exception {
		initialDefaultsInterceptor(model, interceptorContext, modelClass.getSimpleName());
	}
}
