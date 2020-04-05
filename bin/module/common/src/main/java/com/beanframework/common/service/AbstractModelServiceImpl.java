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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.beanframework.common.CommonConstants;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.common.interceptor.ValidateInterceptor;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
@Transactional(readOnly = false)
public abstract class AbstractModelServiceImpl implements ModelService {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected List<InterceptorMapping> interceptorMappings;

	@Autowired
	protected List<ConverterMapping> converterMappings;

	@Value("${interceptor.strict.use:false}")
	protected boolean STRICT_USE_INTERCEPTOR;

	@Override
	public void initialDefaultsInterceptor(Collection models, InterceptorContext context, String typeCode)
			throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			initialDefaultsInterceptor(model, context, typeCode);
		}
	}

	@Override
	public void initialDefaultsInterceptor(Object model, InterceptorContext context, String typeCode)
			throws InterceptorException {

		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof InitialDefaultsInterceptor) {
				InitialDefaultsInterceptor<Object> interceptor = (InitialDefaultsInterceptor<Object>) interceptorMapping
						.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(typeCode)) {
					interceptor.onInitialDefaults(model, context);
					notIntercepted = false;
				}
			}
		}

		if (STRICT_USE_INTERCEPTOR) {
			if (notIntercepted)
				throw new InterceptorException(
						"Cannot find any load interceptor to intercept target typeCode: " + typeCode);
		}
	}

	@Override
	public void loadInterceptor(Collection models, InterceptorContext context, String typeCode)
			throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			loadInterceptor(model, context, typeCode);
		}
	}

	@Override
	public void loadInterceptor(Object model, InterceptorContext context, String typeCode) throws InterceptorException {

		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof LoadInterceptor) {
				LoadInterceptor<Object> interceptor = (LoadInterceptor<Object>) interceptorMapping.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(typeCode)) {
					interceptor.onLoad(model, context);
					notIntercepted = false;
				}
			}
		}

		if (STRICT_USE_INTERCEPTOR) {
			if (notIntercepted)
				throw new InterceptorException(
						"Cannot find any load interceptor to intercept target typeCode: " + typeCode);
		}
	}

	@Override
	public void prepareInterceptor(Collection models, InterceptorContext context, String typeCode)
			throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			prepareInterceptor(model, context, typeCode);
		}
	}

	@Override
	public void prepareInterceptor(Object model, InterceptorContext context, String typeCode)
			throws InterceptorException {

		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof PrepareInterceptor) {
				PrepareInterceptor<Object> interceptor = (PrepareInterceptor<Object>) interceptorMapping
						.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(typeCode)) {
					interceptor.onPrepare(model, context);
					notIntercepted = false;
				}
			}
		}

		if (STRICT_USE_INTERCEPTOR) {
			if (notIntercepted)
				throw new InterceptorException(
						"Cannot find any prepare interceptor to intercept target typeCode: " + typeCode);
		}
	}

	@Override
	public void removeInterceptor(Collection models, InterceptorContext context, String typeCode)
			throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			removeInterceptor(model, context, typeCode);
		}
	}

	@Override
	public void removeInterceptor(Object model, InterceptorContext context, String typeCode)
			throws InterceptorException {

		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof RemoveInterceptor) {
				RemoveInterceptor<Object> interceptor = (RemoveInterceptor<Object>) interceptorMapping.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(typeCode)) {
					interceptor.onRemove(model, context);
					notIntercepted = false;
				}
			}
		}

		if (STRICT_USE_INTERCEPTOR) {
			if (notIntercepted)
				throw new InterceptorException(
						"Cannot find any remove interceptor to intercept target typeCode: " + typeCode);
		}
	}

	@Override
	public void validateInterceptor(Collection models, InterceptorContext context, String typeCode)
			throws InterceptorException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			validateInterceptor(model, context, typeCode);
		}
	}

	@Override
	public void validateInterceptor(Object model, InterceptorContext context, String typeCode)
			throws InterceptorException {

		boolean notIntercepted = true;
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof ValidateInterceptor) {
				ValidateInterceptor<Object> interceptor = (ValidateInterceptor<Object>) interceptorMapping
						.getInterceptor();
				if (interceptorMapping.getTypeCode().equals(typeCode)) {
					interceptor.onValidate(model, context);
					notIntercepted = false;
				}
			}
		}

		if (notIntercepted) {
			if (STRICT_USE_INTERCEPTOR)
				throw new InterceptorException(
						"Cannot find any validate interceptor to intercept target typeCode: " + typeCode);
		}
	}

	@Override
	public void entityConverter(Collection models, EntityConverterContext context, Class modelClass)
			throws ConverterException {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			entityConverter(model, context, modelClass.getSimpleName());
		}
	}

	@Override
	public Object entityConverter(Object model, EntityConverterContext context, String typeCode)
			throws ConverterException {
		for (ConverterMapping interceptorMapping : converterMappings) {
			if (interceptorMapping.getConverter() instanceof EntityConverter) {
				EntityConverter interceptor = (EntityConverter) interceptorMapping.getConverter();
				if (interceptorMapping.getTypeCode().equals(typeCode)) {
					return interceptor.convert(model, context);
				}
			}
		}

		throw new ConverterException("Cannot find any entity convert to convert target model: " + typeCode);
	}

	@Override
	public <T extends Collection> T dtoConverter(Collection models, DtoConverterContext context, String typeCode)
			throws ConverterException, InterceptorException {
		if (models instanceof List<?>) {
			List<Object> listModels = new ArrayList<Object>();
			Iterator iterator = models.iterator();
			while (iterator.hasNext()) {
				Object model = iterator.next();
				listModels.add(dtoConverter(model, context, typeCode));
			}
			return (T) listModels;
		}
		throw new ConverterException("Cannot find available models type to convert target typeCode: " + typeCode);
	}

	@Override
	public Object dtoConverter(Object model, DtoConverterContext context, String typeCode) throws ConverterException {

		for (ConverterMapping interceptorMapping : converterMappings) {
			if (interceptorMapping.getConverter() instanceof DtoConverter) {
				DtoConverter interceptor = (DtoConverter) interceptorMapping.getConverter();
				if (interceptorMapping.getTypeCode().equals(typeCode)) {
					if (context.getConvertedDtos().contains(((GenericEntity) model).getUuid()) == false) {
						if (((GenericEntity) model).getUuid() != null) {
							context.getConvertedDtos().add(((GenericEntity) model).getUuid());
						}
						return interceptor.convert(model, context);
					} else {
						return null;
					}
				}
			}
		}

		throw new ConverterException("Cannot find any dto convert to convert target typeCode: " + typeCode);
	}

	protected <T> Page readPage(TypedQuery query, final Class domainClass, Pageable pageable,
			@Nullable Specification spec) {

		if (pageable.isPaged()) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}

		return PageableExecutionUtils.getPage(query.getResultList(), pageable,
				() -> executeCountQuery(getCountQuery(spec, domainClass)));
	}

	protected <T> TypedQuery getCountQuery(@Nullable Specification spec, Class domainClass) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root root = query.from(domainClass);
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

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
		Assert.notNull(domainClass, "Domain class must not be null!");

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery query = builder.createQuery(domainClass);
		Root root = query.from(domainClass);
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		query.select(root).distinct(true);

		if (sort != null && sort.isSorted()) {
			query.orderBy(toOrders(sort, root, builder));
		}

		return entityManager.createQuery(query);
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

	protected Query createQuery(Map<String, Object> properties, Map<String, Sort.Direction> sorts, String data,
			Integer firstResult, Integer maxResult, Class modelClass) {
		String qlString = "select " + (StringUtils.isBlank(data) ? "o" : data) + " from " + modelClass.getName() + " o";

		if (properties != null && properties.isEmpty() == Boolean.FALSE) {
			qlString = qlString + " where " + sqlProperties(properties);
		}
		if (sorts != null && sorts.isEmpty() == Boolean.FALSE) {
			qlString = qlString + " " + sqlSorts(sorts);
		}

		Query query = entityManager.createQuery(qlString);
		if (properties != null && properties.isEmpty() == Boolean.FALSE) {
			for (Map.Entry<String, Object> entry : properties.entrySet()) {
				if (entry.getValue() != null && (entry.getValue() instanceof String
						&& entry.getValue().toString().trim().equalsIgnoreCase(CommonConstants.Query.IS_EMPTY)) == false) {
					query.setParameter(entry.getKey().replace(".", "_"), entry.getValue());
				}
			}
		}
		if (firstResult != null) {
			if (firstResult > 0) {
				query.setFirstResult(firstResult);
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
					propertiesBuilder.append("o." + entry.getKey() + " "+CommonConstants.Query.IS_NULL);
				} else if (entry.getValue() instanceof String
						&& entry.getValue().toString().trim().equalsIgnoreCase(CommonConstants.Query.IS_EMPTY)) {
					propertiesBuilder.append("o." + entry.getKey() + " "+CommonConstants.Query.IS_EMPTY);
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
				sortsBuilder.append(
						CommonConstants.Query.ORDER_BY + " o." + entry.getKey() + " " + entry.getValue().toString());
			} else {
				sortsBuilder.append(", o." + entry.getKey() + " " + entry.getValue().toString());
			}
		}
		return sortsBuilder.toString();
	}

	protected <T> Page<T> page(@Nullable Specification spec, Pageable pageable, Class modelClass) {

		TypedQuery<T> query = getQuery(spec, pageable, modelClass);
		return pageable.isUnpaged() ? new PageImpl<T>(query.getResultList())
				: readPage(query, modelClass, pageable, spec);
	}
}
