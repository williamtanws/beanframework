package com.beanframework.common.service;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.common.interceptor.ValidateInterceptor;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractModelServiceImpl implements ModelService {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected List<InterceptorMapping> interceptorMappings;

	@Autowired
	protected List<ConverterMapping> converterMappings;

	@Autowired
	protected CacheManager cacheManager;

	protected void initialDefaultsInterceptor(Collection models, Class modelClass) throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			initialDefaultsInterceptor(model, modelClass);
		}
	}

	protected void initialDefaultsInterceptor(Object model, Class modelClass) throws InterceptorException {
		
		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof InitialDefaultsInterceptor) {
				InitialDefaultsInterceptor<Object> interceptor = (InitialDefaultsInterceptor<Object>) interceptorMapping.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(modelClass.getSimpleName())) {
					interceptor.onInitialDefaults(model);
					notIntercepted = false;
				}
			}
		}
		
		if (notIntercepted) {
			throw new InterceptorException("Cannot find any load interceptor to intercept target model: " + modelClass.getSimpleName());
		}
	}
	
	protected void loadInterceptor(Collection models, Class modelClass) throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			loadInterceptor(model, modelClass);
		}
	}

	protected void loadInterceptor(Object model, Class modelClass) throws InterceptorException {
		
		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof LoadInterceptor) {
				LoadInterceptor<Object> interceptor = (LoadInterceptor<Object>) interceptorMapping.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(modelClass.getSimpleName())) {
					interceptor.onLoad(model);
					notIntercepted = false;
				}
			}
		}

		if (notIntercepted) {
			throw new InterceptorException("Cannot find any load interceptor to intercept target model: " + modelClass.getSimpleName());
		}
	}

	protected void prepareInterceptor(Collection models, Class modelClass) throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			prepareInterceptor(model, modelClass);
		}
	}

	protected void prepareInterceptor(Object model, Class modelClass) throws InterceptorException {

		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof PrepareInterceptor) {
				PrepareInterceptor<Object> interceptor = (PrepareInterceptor<Object>) interceptorMapping.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(modelClass.getSimpleName())) {
					interceptor.onPrepare(model);
					notIntercepted = false;
				}
			}
		}

		if (notIntercepted) {
			throw new InterceptorException("Cannot find any prepare interceptor to intercept target model: " + modelClass.getSimpleName());
		}
	}

	protected void removeInterceptor(Collection models, Class modelClass) throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			removeInterceptor(model, modelClass);
		}
	}

	protected void removeInterceptor(Object model, Class modelClass) throws InterceptorException {

		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof RemoveInterceptor) {
				RemoveInterceptor<Object> interceptor = (RemoveInterceptor<Object>) interceptorMapping.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(modelClass.getSimpleName())) {
					interceptor.onRemove(model);
					notIntercepted = false;
				}
			}
		}

		if (notIntercepted) {
			throw new InterceptorException("Cannot find any remove interceptor to intercept target model: " + modelClass.getSimpleName());
		}
	}

	protected void validateInterceptor(Collection models, Class modelClass) throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			validateInterceptor(model, modelClass);
		}
	}

	protected void validateInterceptor(Object model, Class modelClass) throws InterceptorException {

		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof ValidateInterceptor) {
				ValidateInterceptor<Object> interceptor = (ValidateInterceptor<Object>) interceptorMapping.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(modelClass.getSimpleName())) {
					interceptor.onValidate(model);
					notIntercepted = false;
				}
			}
		}

		if (notIntercepted) {
			throw new InterceptorException("Cannot find any validate interceptor to intercept target model: " + modelClass.getSimpleName());
		}
	}

	protected void entityConverter(Collection models, Class modelClass) throws ConverterException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			entityConverter(model, modelClass);
		}
	}

	protected Object entityConverter(Object model, Class modelClass) throws ConverterException {
		for (ConverterMapping interceptorMapping : converterMappings) {
			if (interceptorMapping.getConverter() instanceof EntityConverter) {
				EntityConverter interceptor = (EntityConverter) interceptorMapping.getConverter();
				if (interceptorMapping.getTypeCode().equals(model.getClass().getSimpleName())) {
					return interceptor.convert(model);
				}
			}
		}

		throw new ConverterException("Cannot find any entity convert to convert target model: " + model.getClass());
	}

	protected <T extends Collection> T dtoConverter(Collection models, Class modelClass) throws ConverterException, InterceptorException {
		if (models instanceof List<?>) {
			List<Object> listModels = new ArrayList<Object>();
			Iterator iterator = models.iterator();
			while (iterator.hasNext()) {
				Object model = iterator.next();
				listModels.add(dtoConverter(model, modelClass));
			}
			return (T) listModels;
		}
		throw new ConverterException("Cannot find available models type to convert target model: " + modelClass);
	}

	protected Object dtoConverter(Object model, Class modelClass) throws ConverterException {
		for (ConverterMapping interceptorMapping : converterMappings) {
			if (interceptorMapping.getConverter() instanceof DtoConverter) {
				DtoConverter interceptor = (DtoConverter) interceptorMapping.getConverter();
				if (interceptorMapping.getTypeCode().equals(modelClass.getSimpleName())) {
					return interceptor.convert(model);
				}
			}
		}

		throw new ConverterException("Cannot find any dto convert to convert target model: " + modelClass);
	}

	protected <T> Page<T> page(@Nullable Specification spec, Pageable pageable, Class modelClass) {

		TypedQuery<T> query = getQuery(spec, pageable, modelClass);
		return pageable.isUnpaged() ? new PageImpl<T>(query.getResultList()) : readPage(query, modelClass, pageable, spec);
	}

	protected <T> Page readPage(TypedQuery query, final Class domainClass, Pageable pageable, @Nullable Specification spec) {

		if (pageable.isPaged()) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}

		return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> executeCountQuery(getCountQuery(spec, domainClass)));
	}

	protected <T> TypedQuery getCountQuery(@Nullable Specification spec, Class domainClass) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root root = applySpecificationToCriteria(spec, domainClass, query);

		if (query.isDistinct()) {
			query.select(builder.countDistinct(root));
		} else {
			query.select(builder.count(root));
		}

		// Remove all Orders the Specifications might have applied
		query.orderBy(Collections.<Order>emptyList());

		return entityManager.createQuery(query);
	}

	protected <T> TypedQuery getQuery(@Nullable Specification spec, Pageable pageable, Class modelClass) {

		Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
		return getQuery(spec, modelClass, sort);
	}

	protected <T> TypedQuery getQuery(@Nullable Specification spec, Class domainClass, Sort sort) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery query = builder.createQuery(domainClass);

		Root root = applySpecificationToCriteria(spec, domainClass, query);
		query.select(root);

		if (sort.isSorted()) {
			query.orderBy(toOrders(sort, root, builder));
		}

		return entityManager.createQuery(query);
	}

	protected <S, U extends T, T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass, CriteriaQuery<S> query) {

		Assert.notNull(domainClass, "Domain class must not be null!");
		Assert.notNull(query, "CriteriaQuery must not be null!");

		Root<U> root = query.from(domainClass);

		if (spec == null) {
			return root;
		}

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}

	protected static long executeCountQuery(TypedQuery<Long> query) {

		Assert.notNull(query, "TypedQuery must not be null!");

		List<Long> totals = query.getResultList();
		long total = 0L;

		for (Long element : totals) {
			total += element == null ? 0 : element;
		}

		return total;
	}

	protected Object parseObject(Object value) {
		if (value instanceof Boolean) {

			boolean di = ((Boolean) value).booleanValue();

			if (di) {
				return "1";
			} else {
				return "0";
			}
		} else {
			return value;
		}
	}

	protected <T> T getCachedSingleResult(Map<String, Object> properties, Map<String, Sort.Direction> sorts, String data, Class modelClass) {
		StringBuilder propertiesBuilder = new StringBuilder();
		if (properties != null) {
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
		}

		StringBuilder sortsBuilder = new StringBuilder();
		if (sorts != null) {
			for (Entry<String, Direction> entry : sorts.entrySet()) {
				if (sortsBuilder.length() == 0) {
					sortsBuilder.append("order by o." + entry.getKey() + " " + entry.getValue().toString());
				} else {
					sortsBuilder.append(", o." + entry.getKey() + " " + entry.getValue().toString());
				}
			}
		}

		String qlString = "select " + (StringUtils.isBlank(data) ? "o" : data) + " from " + modelClass.getName() + " o";

		if (propertiesBuilder.length() > 0) {
			qlString = qlString + " where " + propertiesBuilder.toString();
		}
		if (sortsBuilder.length() > 0) {
			qlString = qlString + " " + sortsBuilder.toString();
		}

		ValueWrapper valueWrapper = cacheManager.getCache(modelClass.getName()).get(qlString);
		if (valueWrapper == null) {
			return null;
		} else {
			return (T) valueWrapper.get();
		}
	}

	protected <T extends Collection> T getCachedResultList(Map<String, Object> properties, Map<String, Sort.Direction> sorts, String data, Integer maxResult, Class modelClass) {

		if (properties != null && sorts == null) {
			ValueWrapper valueWrapper = cacheManager.getCache(modelClass.getName()).get("*");
			if (valueWrapper == null) {
				return null;
			} else {
				return (T) valueWrapper.get();
			}
		}

		StringBuilder propertiesBuilder = new StringBuilder();
		if (properties != null) {
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
		}

		StringBuilder sortsBuilder = new StringBuilder();
		if (sorts != null) {
			for (Entry<String, Direction> entry : sorts.entrySet()) {
				if (sortsBuilder.length() == 0) {
					sortsBuilder.append("order by o." + entry.getKey() + " " + entry.getValue().toString());
				} else {
					sortsBuilder.append(", o." + entry.getKey() + " " + entry.getValue().toString());
				}
			}
		}

		String qlString = "select " + (StringUtils.isBlank(data) ? "o" : data) + " from " + modelClass.getName() + " o";

		if (propertiesBuilder.length() > 0) {
			qlString = qlString + " where " + propertiesBuilder.toString();
		}
		if (sortsBuilder.length() > 0) {
			qlString = qlString + " " + sortsBuilder.toString();
		}
		if (maxResult != null) {
			if (maxResult > 0) {
				qlString = qlString + " limit " + maxResult;
			}
		}

		ValueWrapper valueWrapper = cacheManager.getCache(modelClass.getName()).get(qlString);
		if (valueWrapper == null) {
			return null;
		} else {
			return (T) valueWrapper.get();
		}
	}

	protected void setCachedSingleResult(Map<String, Object> properties, Map<String, Sort.Direction> sorts, String data, Class modelClass, Object model) {
		StringBuilder propertiesBuilder = new StringBuilder();
		if (properties != null) {
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
		}

		StringBuilder sortsBuilder = new StringBuilder();
		if (sorts != null) {
			for (Entry<String, Direction> entry : sorts.entrySet()) {
				if (sortsBuilder.length() == 0) {
					sortsBuilder.append("order by o." + entry.getKey() + " " + entry.getValue().toString());
				} else {
					sortsBuilder.append(", o." + entry.getKey() + " " + entry.getValue().toString());
				}
			}
		}

		String qlString = "select " + (StringUtils.isBlank(data) ? "o" : data) + " from " + modelClass.getName() + " o";

		if (propertiesBuilder.length() > 0) {
			qlString = qlString + " where " + propertiesBuilder.toString();
		}
		if (sortsBuilder.length() > 0) {
			qlString = qlString + " " + sortsBuilder.toString();
		}

		cacheManager.getCache(modelClass.getName()).put(qlString, model);
	}

	protected void setCachedResultList(Map<String, Object> properties, Map<String, Sort.Direction> sorts, String data, Integer maxResult, Class modelClass, Collection models) {

		if (properties == null && sorts == null) {
			cacheManager.getCache(modelClass.getName()).put("*", models);
		} else {

			StringBuilder propertiesBuilder = new StringBuilder();
			if (properties != null) {
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
			}

			StringBuilder sortsBuilder = new StringBuilder();
			if (sorts != null) {
				for (Entry<String, Direction> entry : sorts.entrySet()) {
					if (sortsBuilder.length() == 0) {
						sortsBuilder.append("order by o." + entry.getKey() + " " + entry.getValue().toString());
					} else {
						sortsBuilder.append(", o." + entry.getKey() + " " + entry.getValue().toString());
					}
				}
			}

			String qlString = "select " + (StringUtils.isBlank(data) ? "o" : data) + " from " + modelClass.getName() + " o";

			if (propertiesBuilder.length() > 0) {
				qlString = qlString + " where " + propertiesBuilder.toString();
			}
			if (sortsBuilder.length() > 0) {
				qlString = qlString + " " + sortsBuilder.toString();
			}
			if (maxResult != null) {
				if (maxResult > 0) {
					qlString = qlString + " limit " + maxResult;
				}
			}

			cacheManager.getCache(modelClass.getName()).put(qlString, models);
		}
	}

	protected void putCache(Class modelClass, Object key, Object value) {
		cacheManager.getCache(modelClass.getName()).put(key, value);
	}

	protected Query createQuery(Map<String, Object> properties, Map<String, Sort.Direction> sorts, String data, Integer maxResult, Class modelClass) {
		String qlString = "select " + (StringUtils.isBlank(data) ? "o" : data) + " from " + modelClass.getName() + " o";

		if (properties != null && properties.isEmpty() == false) {
			qlString = qlString + " where " + sqlProperties(properties);
		}
		if (sorts != null && sorts.isEmpty() == false) {
			qlString = qlString + " " + sqlSorts(sorts);
		}

		Query query = entityManager.createQuery(qlString);
		if (properties != null && properties.isEmpty() == false) {
			for (Map.Entry<String, Object> entry : properties.entrySet()) {
				if (entry.getValue() != null) {
					query.setParameter(entry.getKey().replace(".", "_"), entry.getValue());
				}
			}
		}
		if (maxResult != null) {
			if (maxResult > 0) {
				query.setMaxResults(maxResult);
			}
		}

		return query;
	}

	protected String sqlProperties(Map<String, Object> properties) {
		StringBuilder propertiesBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (propertiesBuilder.length() == 0) {
				if (entry.getValue() == null) {
					propertiesBuilder.append("o." + entry.getKey() + " IS NULL");
				} else {
					propertiesBuilder.append("o." + entry.getKey() + " = :" + entry.getKey().replace(".", "_"));
				}
			} else {
				propertiesBuilder.append(" and o." + entry.getKey() + " = :" + entry.getKey().replace(".", "_"));
			}
		}
		return propertiesBuilder.toString();
	}

	protected String sqlSorts(Map<String, Sort.Direction> sorts) {
		StringBuilder sortsBuilder = new StringBuilder();
		for (Entry<String, Direction> entry : sorts.entrySet()) {
			if (sortsBuilder.length() == 0) {
				sortsBuilder.append("order by o." + entry.getKey() + " " + entry.getValue().toString());
			} else {
				sortsBuilder.append(", o." + entry.getKey() + " " + entry.getValue().toString());
			}
		}
		return sortsBuilder.toString();
	}
}
