package com.beanframework.common.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterRemoveEvent;
import com.beanframework.common.registry.AfterRemoveListener;
import com.beanframework.common.registry.AfterRemoveListenerRegistry;
import com.beanframework.common.registry.AfterSaveEvent;
import com.beanframework.common.registry.AfterSaveListener;
import com.beanframework.common.registry.AfterSaveListenerRegistry;
import com.beanframework.common.registry.BeforeRemoveEvent;
import com.beanframework.common.registry.BeforeRemoveListener;
import com.beanframework.common.registry.BeforeRemoveListenerRegistry;
import com.beanframework.common.registry.BeforeSaveEvent;
import com.beanframework.common.registry.BeforeSaveListener;
import com.beanframework.common.registry.BeforeSaveListenerRegistry;
import com.beanframework.common.repository.ModelRepository;
import com.beanframework.common.specification.AbstractSpecification;

@SuppressWarnings({"rawtypes", "unchecked"})
@Service
@Transactional
public class ModelServiceImpl extends AbstractModelServiceImpl {

  @Autowired
  private ModelRepository modelRepository;

  @Autowired
  private BeforeSaveListenerRegistry beforeSaveListenerRegistry;

  @Autowired
  private AfterSaveListenerRegistry afterSaveListenerRegistry;

  @Autowired
  private BeforeRemoveListenerRegistry beforeRemoveListenerRegistry;

  @Autowired
  private AfterRemoveListenerRegistry afterRemoveListenerRegistry;

  @Override
  public void attach(Object model) {
    entityManager.merge(model);
  }

  @Override
  public void detach(Object model) {
    entityManager.detach(model);
  }

  @Override
  public void detachAll() {
    entityManager.clear();
  }

  @Override
  public <T> T merge(Object model) {
    return (T) entityManager.merge(model);
  }

  @Override
  public void refresh(Object model) {
    entityManager.refresh(model);
  }

  @Override
  public void flush() {
    modelRepository.flush();
  }

  @Override
  public <T> T create(Class modelClass)
      throws InstantiationException, IllegalAccessException, InterceptorException {
    Assert.notNull(modelClass, "modelClass was null");

    Object model = modelClass.newInstance();
    initialDefaultsInterceptor(model, modelClass.getSimpleName());
    return (T) model;
  }

  @Override
  public <T> T findOneByUuid(UUID uuid, Class modelClass) throws InterceptorException {
    Assert.notNull(uuid, "uuid was null");
    Assert.notNull(modelClass, "modelClass was null");

    Object model = entityManager.find(modelClass, uuid);

    if (model != null)
      loadInterceptor(model, modelClass.getSimpleName());

    return (T) model;
  }

  @Override
  public <T> T findOneByUuid(UUID uuid, Class modelClass, String typeCode)
      throws InterceptorException {
    Assert.notNull(uuid, "uuid was null");
    Assert.notNull(typeCode, "typeCode was null");

    Object model = entityManager.find(modelClass, uuid);

    if (model != null)
      loadInterceptor(model, typeCode);

    return (T) model;
  }

  @Override
  public <T> T findOneByProperties(Map<String, Object> properties, Class modelClass)
      throws InterceptorException {
    Assert.notNull(modelClass, "modelClass was null");

    try {
      Object model = createQuery(properties, null, null, null, null, modelClass).getSingleResult();

      if (model != null)
        loadInterceptor(model, modelClass.getSimpleName());

      return (T) model;
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public int countAll(Class modelClass) {
    Long count =
        (Long) createQuery(null, null, "count(o)", null, null, modelClass).getSingleResult();

    return count.intValue();
  }

  @Override
  public int countByProperties(Map<String, Object> properties, Class modelClass) {
    Long count =
        (Long) createQuery(properties, null, "count(o)", null, null, modelClass).getSingleResult();

    return count.intValue();
  }

  @Override
  public int countBySpecification(AbstractSpecification specification, Class modelClass) {
    Long count = (Long) executeCountQuery(getCountQuery(specification, modelClass));

    return count.equals(0L) ? 0 : Integer.valueOf(count.intValue());
  }

  @Override
  public boolean existsByProperties(Map<String, Object> properties, Class modelClass) {
    Assert.notNull(properties, "properties was null");
    Assert.notNull(modelClass, "modelClass was null");

    Long count =
        (Long) createQuery(properties, null, "count(o)", null, null, modelClass).getSingleResult();

    return count.equals(0L) ? false : true;
  }

  @Override
  public <T extends Collection> T findByProperties(Map<String, Object> properties, Class modelClass)
      throws InterceptorException {
    Assert.notNull(modelClass, "modelClass was null");

    List<Object> models =
        createQuery(properties, null, null, null, null, modelClass).getResultList();

    if (models != null)
      loadInterceptor(models, modelClass.getSimpleName());

    return (T) models;
  }

  @Override
  public <T extends Collection> T findByPropertiesBySortByResult(Map<String, Object> properties,
      Map<String, Sort.Direction> sorts, Integer firstResult, Integer maxResult, Class modelClass)
      throws InterceptorException {
    Assert.notNull(modelClass, "modelClass was null");

    List<Object> models =
        createQuery(properties, sorts, null, firstResult, maxResult, modelClass).getResultList();

    if (models != null)
      loadInterceptor(models, modelClass.getSimpleName());

    return (T) models;
  }

  @Override
  public <T extends Collection> T findBySpecificationBySort(AbstractSpecification specification,
      Sort sort, Class modelClass) throws InterceptorException {
    Assert.notNull(modelClass, "modelClass was null");

    List<Object> models = getQuery(specification, modelClass, sort).getResultList();

    if (models != null)
      loadInterceptor(models, modelClass.getSimpleName());

    return (T) models;
  }

  @Override
  public <T extends Collection> T findBySpecification(AbstractSpecification specification,
      Class modelClass) throws InterceptorException {
    Assert.notNull(modelClass, "modelClass was null");

    List<Object> models = getQuery(specification, modelClass, null).getResultList();

    if (models != null)
      loadInterceptor(models, modelClass.getSimpleName());

    return (T) models;
  }

  @Override
  public <T extends Collection> T findAll(Class modelClass) {
    return (T) createQuery(null, null, null, null, null, modelClass).getResultList();
  }

  @Transactional(readOnly = true)
  @Override
  public List<?> searchByQuery(String qlString) {
    return entityManager.createQuery(qlString).getResultList();
  }

  @Override
  public Query createNativeQuery(String sqlString) {
    return entityManager.createNativeQuery(sqlString);
  }

  @Override
  public List<Object[]> findHistory(boolean selectDeletedEntities,
      List<AuditCriterion> auditCriterions, List<AuditOrder> auditOrders, Integer firstResult,
      Integer maxResults, Class modelClass) {

    // Create the Audit Reader. It uses the EntityManager, which will be opened when
    // starting the new Transation and closed when the Transaction finishes.
    AuditReader auditReader = AuditReaderFactory.get(entityManager);

    // Create the Query:
    AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntityWithChanges(modelClass,
        selectDeletedEntities);

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

  @Override
  public int countHistory(boolean selectDeletedEntities, List<AuditCriterion> auditCriterions,
      List<AuditOrder> auditOrders, Class modelClass) {

    // Create the Audit Reader. It uses the EntityManager, which will be opened when
    // starting the new Transation and closed when the Transaction finishes.
    AuditReader auditReader = AuditReaderFactory.get(entityManager);

    // Create the Query:
    AuditQuery auditQuery =
        auditReader.createQuery().forRevisionsOfEntityWithChanges(modelClass, selectDeletedEntities)
            .addProjection(AuditEntity.id().count());

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

    Long count = (Long) auditQuery.getSingleResult();
    return count.intValue();
  }

  @Override
  public <T> Page<T> findPage(AbstractSpecification spec, Pageable pageable, Class modelClass)
      throws InterceptorException {
    Page<T> page = (Page<T>) page(spec, pageable, modelClass);

    loadInterceptor(page.getContent(), modelClass.getSimpleName());

    return page;
  }

  @Override
  public Object saveEntity(Object model) throws InterceptorException, ListenerException {

    if (model == null) {
      return null;
    }

    BeforeSaveEvent beforeSaveEvent = new BeforeSaveEvent(BeforeSaveEvent.CREATE);
    AfterSaveEvent afterSaveEvent = new AfterSaveEvent(BeforeSaveEvent.CREATE);
    if (((GenericEntity) model).getUuid() != null) {
      Map<String, Object> properties = new HashMap<String, Object>();
      properties.put(GenericEntity.UUID, ((GenericEntity) model).getUuid());
      if (existsByProperties(properties, model.getClass())) {
        beforeSaveEvent = new BeforeSaveEvent(BeforeSaveEvent.UPDATE);
        afterSaveEvent = new AfterSaveEvent(BeforeSaveEvent.UPDATE);
      }
    }

    prepareInterceptor(model, model.getClass().getSimpleName());
    validateInterceptor(model, model.getClass().getSimpleName());

    Set<Entry<String, BeforeSaveListener>> beforeSaveListeners =
        beforeSaveListenerRegistry.getListeners().entrySet();
    for (Entry<String, BeforeSaveListener> entry : beforeSaveListeners) {
      entry.getValue().beforeSave(model, beforeSaveEvent);
    }

    ((GenericEntity) model).setLastModifiedDate(new Date());
    model = modelRepository.save(model);

    Set<Entry<String, AfterSaveListener>> afterSaveListeners =
        afterSaveListenerRegistry.getListeners().entrySet();
    for (Entry<String, AfterSaveListener> entry : afterSaveListeners) {
      entry.getValue().afterSave(model, afterSaveEvent);
    }

    model = findOneByUuid(((GenericEntity) model).getUuid(), model.getClass());

    return model;
  }

  @Override
  public Object saveEntityByLegacyMode(Object model) {

    if (model == null) {
      return null;
    }
    ((GenericEntity) model).setLastModifiedDate(new Date());
    model = modelRepository.save(model);
    return model;
  }

  @Override
  public void deleteEntity(Object model, Class modelClass)
      throws InterceptorException, ListenerException {

    if (model != null) {
      removeInterceptor(model, modelClass.getSimpleName());

      BeforeRemoveEvent beforeRemoveEvent = new BeforeRemoveEvent();
      Set<Entry<String, BeforeRemoveListener>> beforeRemoveListeners =
          beforeRemoveListenerRegistry.getListeners().entrySet();
      for (Entry<String, BeforeRemoveListener> entry : beforeRemoveListeners) {
        entry.getValue().beforeRemove(model, beforeRemoveEvent);
      }

      ((GenericEntity) model).setLastModifiedDate(new Date());
      modelRepository.delete(model);

      AfterRemoveEvent afterRemoveEvent = new AfterRemoveEvent(beforeRemoveEvent.getDataMap());
      Set<Entry<String, AfterRemoveListener>> afterRemoveListeners =
          afterRemoveListenerRegistry.getListeners().entrySet();
      for (Entry<String, AfterRemoveListener> entry : afterRemoveListeners) {
        entry.getValue().afterRemove(model, afterRemoveEvent);
      }
    }
  }

  @Override
  public void deleteEntityByLegacyMode(Object model, Class modelClass) {
    if (model != null) {
      ((GenericEntity) model).setLastModifiedDate(new Date());
      modelRepository.delete(model);
    }
  }

  @Override
  public void deleteByUuid(UUID uuid, Class modelClass)
      throws InterceptorException, ListenerException {

    Object model = findOneByUuid(uuid, modelClass);

    if (model != null)
      deleteEntity(model, modelClass);

  }

  @Override
  public void deleteAll(Class modelClass) throws InterceptorException, ListenerException {
    Collection models = findAll(modelClass);
    for (Object model : models) {
      deleteEntity(model, modelClass);
    }
  }

  @Override
  public void deleteEntityByLegacyModeByUuid(UUID uuid, Class modelClass)
      throws InterceptorException {
    Object model = findOneByUuid(uuid, modelClass);

    if (model != null)
      deleteEntityByLegacyMode(model, modelClass);
  }

  @Override
  public <T> T getEntity(Object model, Class modelClass) throws ConverterException {
    if (model == null) {
      return null;
    }

    model = entityConverter(model, modelClass.getSimpleName());
    return (T) model;
  }

  @Override
  public <T> T getEntity(Object model, String typeCode) throws ConverterException {
    if (model == null) {
      return null;
    }

    model = entityConverter(model, typeCode);
    return (T) model;
  }

  @Override
  public <T extends Collection> T getEntityList(Collection models, Class modelClass)
      throws ConverterException {

    if (models == null)
      return null;

    if (models.isEmpty())
      return (T) new ArrayList<T>();

    List<Object> entityObjects = new ArrayList<Object>();
    for (Object model : models) {
      model = entityConverter(model, modelClass.getSimpleName());
      entityObjects.add(model);
    }
    return (T) entityObjects;
  }

  @Override
  public <T extends Collection> T getEntityList(Collection models, String typeCode)
      throws ConverterException {
    if (models == null)
      return null;

    if (models.isEmpty())
      return (T) new ArrayList<T>();

    List<Object> entityObjects = new ArrayList<Object>();
    for (Object model : models) {
      model = entityConverter(model, typeCode);
      entityObjects.add(model);
    }
    return (T) entityObjects;
  }

  @Override
  public <T> T getDto(Object model, Class modelClass) throws ConverterException {

    if (model == null)
      return null;

    model = dtoConverter(model, modelClass.getSimpleName());
    return (T) model;
  }

  @Override
  public <T> T getDto(Object model, String typeCode) throws ConverterException {

    if (model == null)
      return null;

    model = dtoConverter(model, typeCode);
    return (T) model;
  }

  @Override
  public <T extends Collection> T getDtoList(Collection models, Class modelClass)
      throws ConverterException {

    if (models == null)
      return null;

    if (models.isEmpty())
      return (T) new ArrayList<T>();

    List<Object> dtoObjects = new ArrayList<Object>();
    for (Object model : models) {
      model = dtoConverter(model, modelClass.getSimpleName());
      dtoObjects.add(model);
    }
    return (T) dtoObjects;
  }

  @Override
  public <T extends Collection> T getDtoList(Collection models, String typeCode)
      throws ConverterException {

    if (models == null)
      return null;

    if (models.isEmpty())
      return (T) new ArrayList<T>();

    List<Object> dtoObjects = new ArrayList<Object>();
    for (Object model : models) {
      model = dtoConverter(model, typeCode);
      dtoObjects.add(model);
    }
    return (T) dtoObjects;
  }

  @Override
  public void initDefaults(Object model, Class modelClass) throws InterceptorException {
    initialDefaultsInterceptor(model, modelClass.getSimpleName());
  }
}
