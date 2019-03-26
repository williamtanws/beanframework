package com.beanframework.cronjob.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobLoadInterceptor extends AbstractLoadInterceptor<Cronjob> {

	@Override
	public void onLoad(Cronjob model, InterceptorContext context) throws InterceptorException {
		
		if (context.isFetchable(Cronjob.class, Cronjob.CRONJOB_DATAS))
			Hibernate.initialize(model.getCronjobDatas());
		
		super.onLoad(model, context);
	}

}
