package com.beanframework.common.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.ModelInitializationException;
import com.beanframework.common.exception.ModelRemovalException;
import com.beanframework.common.exception.ModelSavingException;
import com.beanframework.common.repository.ModelRepository;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class ModelServiceImpl extends AbstractModelServiceImpl implements ModelService {

	@Autowired
	private ModelRepository modelRepository;

	@Override
	public void attach(Object object) {
		entityManager.merge(object);
	}

	@Override
	public void detach(Object object) {
		entityManager.detach(object);
	}

	@Override
	public void detachAll() {
		entityManager.clear();
	}

	@Override
	public <T> T create(Class objectClass) {
		Object object = null;
		try {
			object = objectClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialDefaultsInterceptor(object);
		return (T) object;
	}

	@Override
	public <T> T findOneByFields(Map<String, String> fields, Class var2) {
		StringBuilder fieldsBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			if (fieldsBuilder.length() == 0) {
				fieldsBuilder.append("o." + entry.getKey() + " = :" + entry.getKey());
			} else {
				fieldsBuilder.append(" and o." + entry.getKey() + " = :" + entry.getKey());
			}
		}

		Query query = entityManager
				.createQuery("select o from " + var2.getName() + " o where " + fieldsBuilder.toString());

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		Object model;
		try {
			model = (T) query.getSingleResult();
			
			loadInterceptor(model);

			return (T) model;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public <T> T findByUuid(UUID uuid, Class objectClass) {
		Object model = entityManager.find(objectClass, uuid);

		loadInterceptor(model);

		return (T) model;
	}

	@Override
	public <T extends Collection> T findByParameters(Map<String, String> fields, Class objectClass) {
		StringBuilder fieldsBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			if (fieldsBuilder.length() == 0) {
				fieldsBuilder.append("o." + entry.getKey() + " = :" + entry.getKey());
			} else {
				fieldsBuilder.append(" and o." + entry.getKey() + " = :" + entry.getKey());
			}
		}

		Query query = entityManager
				.createQuery("select o from " + objectClass.getName() + " o where " + fieldsBuilder.toString());

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		Collection models = (T) query.getResultList();

		for (Object model : models) {
			loadInterceptor(model);
		}

		return (T) models;
	}

	@Override
	public <T extends Collection> T findAll() {
		Collection models = (T) modelRepository.findAll();

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			loadInterceptor(iterator.next());
		}

		return (T) models;
	}

	@Override
	public <T> Page<T> findPage(@Nullable Specification spec, Pageable pageable, Class objectClass) {
		Page<T> page = (Page<T>) page(spec, pageable, objectClass);

		Iterator iterator = page.getContent().iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			loadInterceptor(model);
		}

		return page;
	}

	@Override
	public void refresh(Object object) {
		entityManager.refresh(object);
	}

	@Override
	public void save(Object object) throws ModelSavingException {
		validateInterceptor(object);
		prepareInterceptor(object);
		modelRepository.save(object);
	}

	@Override
	public void saveAll() throws ModelSavingException {
		modelRepository.flush();
	}

	@Override
	public void remove(Object object) throws ModelRemovalException {
		modelRepository.delete(object);
		removeInterceptor(object);
	}

	@Override
	public void remove(UUID uuid, Class objectClass) throws ModelRemovalException {
		Object model = entityManager.find(objectClass, uuid);
		
		if(model == null) {
			throw new ModelRemovalException("UUID not exists.", new Exception());
		}
		
		modelRepository.delete(model);
		removeInterceptor(model);
	}

	@Override
	public void removeAll() throws ModelRemovalException {
		modelRepository.deleteAll();
	}

	@Override
	public <T> T getEntity(Object object) {
		entityConverter(object);
		return (T) object;
	}

	@Override
	public <T> T getDto(Object object) {
		dtoConverter(object);
		return (T) object;
	}

	@Override
	public void initDefaults(Object object) throws ModelInitializationException {
		initialDefaultsInterceptor(object);
	}
}
