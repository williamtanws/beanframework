package com.beanframework.cronjob.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobValidateInterceptor implements ValidateInterceptor<Cronjob> {

	@Override
	public void onValidate(Cronjob model) throws InterceptorException {
		
	}

}
