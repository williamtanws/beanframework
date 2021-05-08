package com.beanframework.trainingcore.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.training.domain.Training;

public class TrainingPrepareInterceptor extends AbstractPrepareInterceptor<Training> {

	@Override
	public void onPrepare(Training model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
	}

}
