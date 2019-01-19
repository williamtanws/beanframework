package com.beanframework.cronjob.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobInitializeInterceptor implements InitializeInterceptor<Cronjob> {

	@Override
	public void onInitialize(Cronjob model) throws InterceptorException {
		Hibernate.initialize(model.getCronjobDatas());
	}

}
