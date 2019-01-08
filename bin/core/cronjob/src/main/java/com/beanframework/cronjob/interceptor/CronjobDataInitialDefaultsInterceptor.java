package com.beanframework.cronjob.interceptor;

import java.util.UUID;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.cronjob.domain.CronjobData;

public class CronjobDataInitialDefaultsInterceptor implements InitialDefaultsInterceptor<CronjobData> {

	@Override
	public void onInitialDefaults(CronjobData model) throws InterceptorException {
	}

}
