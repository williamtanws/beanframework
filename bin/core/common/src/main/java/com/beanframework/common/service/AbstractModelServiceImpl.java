package com.beanframework.common.service;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	private List<InterceptorMapping> interceptorMappings;

	@Autowired
	private List<ConverterMapping> converterMappings;
	
	public void initialDefaultsInterceptor(Collection<? extends Object> models) {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			initialDefaultsInterceptor(model);
		}
	}

	public void initialDefaultsInterceptor(Object model) {
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof InitialDefaultsInterceptor) {
				InitialDefaultsInterceptor<Object> interceptor = (InitialDefaultsInterceptor<Object>) interceptorMapping
						.getInterceptor();
				try {
					if (interceptorMapping.getTypeCode().equals(model.getClass().getSimpleName())) {
						interceptor.onInitialDefaults(model);
					}
				} catch (InterceptorException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void loadInterceptor(Collection<? extends Object> models) {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			loadInterceptor(model);
		}
	}

	public void loadInterceptor(Object model) {
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof LoadInterceptor) {
				LoadInterceptor<Object> interceptor = (LoadInterceptor<Object>) interceptorMapping.getInterceptor();
				try {
					if (interceptorMapping.getTypeCode().equals(model.getClass().getSimpleName())) {
						interceptor.onLoad(model);
					}
				} catch (InterceptorException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void prepareInterceptor(Collection<? extends Object> models) {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			prepareInterceptor(model);
		}
	}

	public void prepareInterceptor(Object model) {
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof PrepareInterceptor) {
				PrepareInterceptor<Object> interceptor = (PrepareInterceptor<Object>) interceptorMapping
						.getInterceptor();
				try {
					if (interceptorMapping.getTypeCode().equals(model.getClass().getSimpleName())) {
						interceptor.onPrepare(model);
					}
				} catch (InterceptorException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void removeInterceptor(Collection<? extends Object> models) {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			removeInterceptor(model);
		}
	}

	public void removeInterceptor(Object model) {
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof RemoveInterceptor) {
				RemoveInterceptor<Object> interceptor = (RemoveInterceptor<Object>) interceptorMapping.getInterceptor();
				try {
					if (interceptorMapping.getTypeCode().equals(model.getClass().getSimpleName())) {
						interceptor.onRemove(model);
					}
				} catch (InterceptorException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void validateInterceptor(Collection<? extends Object> models) {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			validateInterceptor(model);
		}
	}

	public void validateInterceptor(Object model) {
		for (InterceptorMapping interceptorMapping : interceptorMappings) {
			if (interceptorMapping.getInterceptor() instanceof ValidateInterceptor) {
				ValidateInterceptor<Object> interceptor = (ValidateInterceptor<Object>) interceptorMapping
						.getInterceptor();
				try {
					if (interceptorMapping.getTypeCode().equals(model.getClass().getSimpleName())) {
						interceptor.onValidate(model);
					}
				} catch (InterceptorException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void entityConverter(Collection<? extends Object> models) {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			entityConverter(model);
		}
	}

	public Object entityConverter(Object model) {
		for (ConverterMapping interceptorMapping : converterMappings) {
			if (interceptorMapping.getConverter() instanceof EntityConverter) {
				EntityConverter interceptor = (EntityConverter) interceptorMapping.getConverter();
				try {
					if (interceptorMapping.getTypeCode().equals(model.getClass().getSimpleName())) {
						return interceptor.convert(model);
					}
				} catch (ConverterException e) {
					e.printStackTrace();
				}
			}
		}

		return model;
	}
	
	public void dtoConverter(Collection<? extends Object> models) {

		Iterator iterator = models.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			dtoConverter(model);
		}
	}

	public Object dtoConverter(Object model) {
		for (ConverterMapping interceptorMapping : converterMappings) {
			if (interceptorMapping.getConverter() instanceof DtoConverter) {
				DtoConverter interceptor = (DtoConverter) interceptorMapping.getConverter();
				try {
					if (interceptorMapping.getTypeCode().equals(model.getClass().getSimpleName())) {
						return interceptor.convert(model);
					}
				} catch (ConverterException e) {
					e.printStackTrace();
				}
			}
		}

		return model;
	}

	protected <T> Page<T> page(@Nullable Specification spec, Pageable pageable, Class objectClass) {

		TypedQuery<T> query = getQuery(spec, pageable, objectClass);
		return pageable.isUnpaged() ? new PageImpl<T>(query.getResultList())
				: readPage(query, objectClass, pageable, spec);
	}

	private <T> Page readPage(TypedQuery query, final Class domainClass, Pageable pageable,
			@Nullable Specification spec) {

		if (pageable.isPaged()) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}

		return PageableExecutionUtils.getPage(query.getResultList(), pageable,
				() -> executeCountQuery(getCountQuery(spec, domainClass)));
	}

	private <T> TypedQuery getCountQuery(@Nullable Specification spec, Class domainClass) {

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

	private <T> TypedQuery getQuery(@Nullable Specification spec, Pageable pageable, Class objectClass) {

		Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
		return getQuery(spec, objectClass, sort);
	}

	private <T> TypedQuery getQuery(@Nullable Specification spec, Class domainClass, Sort sort) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery query = builder.createQuery(domainClass);

		Root root = applySpecificationToCriteria(spec, domainClass, query);
		query.select(root);

		if (sort.isSorted()) {
			query.orderBy(toOrders(sort, root, builder));
		}

		return entityManager.createQuery(query);
	}

	private <S, U extends T, T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec,
			Class<U> domainClass, CriteriaQuery<S> query) {

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

	private static long executeCountQuery(TypedQuery<Long> query) {

		Assert.notNull(query, "TypedQuery must not be null!");

		List<Long> totals = query.getResultList();
		long total = 0L;

		for (Long element : totals) {
			total += element == null ? 0 : element;
		}

		return total;
	}
}
