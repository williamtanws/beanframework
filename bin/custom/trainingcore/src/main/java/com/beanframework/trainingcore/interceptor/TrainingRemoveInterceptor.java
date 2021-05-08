package com.beanframework.trainingcore.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.training.domain.Training;

public class TrainingRemoveInterceptor extends AbstractRemoveInterceptor<Training> {

	@Override
	public void onRemove(Training model, InterceptorContext context) throws InterceptorException {
		try {
			
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
