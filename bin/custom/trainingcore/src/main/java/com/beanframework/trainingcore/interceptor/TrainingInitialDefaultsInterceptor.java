package com.beanframework.trainingcore.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.training.domain.Training;

public class TrainingInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Training> {

	@Override
	public void onInitialDefaults(Training model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
