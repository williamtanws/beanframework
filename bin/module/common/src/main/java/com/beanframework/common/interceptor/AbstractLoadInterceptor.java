package com.beanframework.common.interceptor;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.InterceptorException;

public abstract class AbstractLoadInterceptor<T extends GenericEntity> implements LoadInterceptor<T> {

	@Override
	public T onLoad(T model, InterceptorContext context) throws InterceptorException {
		return model;
	}

	protected void loadCommonProperties(T source, T prototype, InterceptorContext context) {
		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
	}

	public Object unproxy(Object object) {
		if (object instanceof HibernateProxy) {
			object = (Object) Hibernate.unproxy(object);
		}
		return object;
	}

	public List<Object> unproxy(List<Object> objects) {
		for (int i = 0; i < objects.size(); i++) {
			if (objects instanceof HibernateProxy)
				objects.set(i, Hibernate.unproxy(objects.get(i)));
		}

		return objects;
	}
}
