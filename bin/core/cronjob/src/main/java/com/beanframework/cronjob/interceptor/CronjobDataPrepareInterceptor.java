package com.beanframework.cronjob.interceptor;

import java.util.UUID;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.cronjob.domain.CronjobData;

public class CronjobDataPrepareInterceptor implements PrepareInterceptor<CronjobData> {

	@Override
	public void onPrepare(CronjobData model) throws InterceptorException {
		if (model.getId() == null) {
			model.setId(UUID.randomUUID().toString().replace("-", ""));
		}
	}

}
