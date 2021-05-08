package com.beanframework.trainingcore.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.training.domain.Training;

public class TrainingLoadInterceptor extends AbstractLoadInterceptor<Training> {

	@Override
	public void onLoad(Training model, InterceptorContext context) throws InterceptorException {

	}

}
