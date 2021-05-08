package com.beanframework.trainingcore.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.training.domain.Training;

public class TrainingValidateInterceptor extends AbstractValidateInterceptor<Training> {

	@Override
	public void onValidate(Training model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
