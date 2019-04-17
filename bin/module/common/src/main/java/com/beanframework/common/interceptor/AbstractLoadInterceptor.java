package com.beanframework.common.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.InterceptorException;

public abstract class AbstractLoadInterceptor<T extends GenericEntity> implements LoadInterceptor<T> {

	@Override
	public void onLoad(T model, InterceptorContext context) throws InterceptorException {
	}

	protected void loadCommonProperties(T source, T prototype, InterceptorContext context) {
		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
	}
}
