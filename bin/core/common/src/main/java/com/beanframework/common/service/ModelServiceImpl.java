package com.beanframework.common.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.NoResultException;

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
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(GenericDomain.UUID, uuid);

			Object model = (T) findOneEntityByProperties(properties, modelClass);
			loadInterceptor(model, modelClass);

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
	public <T extends Collection> T findEntityByProperties(Map<String, Object> properties, Class modelClass) throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {
			return (T) findyPropertiesAndSorts(properties, null, modelClass, false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findEntityBySorts(Map<String, Sort.Direction> sorts, Class modelClass) throws Exception {
		Assert.notNull(sorts, "sorts was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {
			return (T) findyPropertiesAndSorts(null, sorts, modelClass, false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findEntityByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Class modelClass) throws Exception {
		if (properties == null && sorts == null) {
			Assert.notNull(properties, "properties was null");
			Assert.notNull(sorts, "sorts was null");
		}
		Assert.notNull(modelClass, "modelClass was null");

		try {
			return (T) findyPropertiesAndSorts(properties, sorts, modelClass, false);
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
					model = createQuery(properties, null, null, modelClass).getSingleResult();

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

	private <T extends Collection> T findyPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Class modelClass, boolean dto) throws Exception {
		if (properties == null && sorts == null) {
			Assert.notNull(properties, "properties was null");
			Assert.notNull(sorts, "sorts was null");
		}
		Assert.notNull(modelClass, "modelClass was null");

		try {
			List<Object> models = getCachedResultList(properties, sorts, null, modelClass);

			if (models == null) {
				models = createQuery(properties, sorts, null, modelClass).getResultList();

				setCachedResultList(properties, sorts, null, modelClass, models);
			}

			if (models != null & models.isEmpty() == false) {
				loadInterceptor(models, modelClass);
				if (dto) {
					models = getDto(models, modelClass);
				}
			}

			return (T) models;
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
				count = (Long) createQuery(properties, null, "count(o)", modelClass).getSingleResult();

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
	public <T extends Collection> T findDtoByProperties(Map<String, Object> properties, Class modelClass) throws Exception {
		Assert.notNull(properties, "properties was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {
			List<Object> models = getCachedResultList(properties, null, null, modelClass);

			if (models == null) {

				models = createQuery(properties, null, null, modelClass).getResultList();

				setCachedResultList(properties, null, null, modelClass, models);
			}

			if (models != null & models.isEmpty() == false) {
				loadInterceptor(models, modelClass);
				models = getDto(models, modelClass);
			}

			return (T) models;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findDtoBySorts(Map<String, Sort.Direction> sorts, Class modelClass) throws Exception {
		Assert.notNull(sorts, "sorts was null");
		Assert.notNull(modelClass, "modelClass was null");

		try {
			List<Object> models = getCachedResultList(null, sorts, null, modelClass);

			if (models == null) {
				models = createQuery(null, sorts, null, modelClass).getResultList();

				setCachedResultList(null, sorts, null, modelClass, models);
			}

			if (models != null & models.isEmpty() == false) {
				loadInterceptor(models, modelClass);
				models = getDto(models, modelClass);
			}

			return (T) models;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findDtoByPropertiesAndSorts(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Class modelClass) throws Exception {
		if (properties == null && sorts == null) {
			Assert.notNull(properties, "properties was null");
			Assert.notNull(sorts, "sorts was null");
		}
		Assert.notNull(modelClass, "modelClass was null");

		try {
			return (T) findyPropertiesAndSorts(properties, sorts, modelClass, true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T extends Collection> T findAll(Class modelClass) throws Exception {

		try {
			List<Object> models = getCachedResultList(null, null, null, modelClass);

			if (models == null) {
				models = createQuery(null, null, null, modelClass).getResultList();

				setCachedResultList(null, null, null, modelClass, models);
			}

			if (models != null & models.isEmpty() == false) {
				loadInterceptor(models, modelClass);
				models = getDto(models, modelClass);
			}

			return (T) models;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public <T> Page<T> findPage(Specification spec, Pageable pageable, Class modelClass) throws Exception {
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
	public void saveEntity(Object model, Class modelClass) throws BusinessException {

		try {
			prepareInterceptor(model, modelClass);
			validateInterceptor(model, modelClass);
			modelRepository.save(model);
			clearCache(modelClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void saveDto(Object model, Class modelClass) throws BusinessException {

		try {
			model = entityConverter(model, modelClass);
			prepareInterceptor(model, modelClass);
			validateInterceptor(model, modelClass);
			modelRepository.save(model);

			clearCache(modelClass);

			model = getDto(model, modelClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void saveAll() throws BusinessException {
		modelRepository.flush();
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void delete(UUID uuid, Class modelClass) throws BusinessException {

		try {

			Object model;
			try {
				model = findOneEntityByUuid(uuid, modelClass);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage(), e);
			}

			removeInterceptor(model, modelClass);

			clearCache(modelClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public void deleteAll(Class modelClass) throws BusinessException {
		try {
			List<Object> models = findAll(modelClass);
			for (Object model : models) {
				modelRepository.delete(model);
			}

			clearCache(modelClass);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
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
