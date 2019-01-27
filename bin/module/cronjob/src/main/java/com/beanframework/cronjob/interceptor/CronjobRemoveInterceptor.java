package com.beanframework.cronjob.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobRemoveInterceptor implements RemoveInterceptor<Cronjob> {

	@Override
	public void onRemove(Cronjob model) throws InterceptorException {
	}

}
