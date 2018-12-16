package com.beanframework.cronjob.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobPrepareInterceptor implements PrepareInterceptor<Cronjob> {
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void onPrepare(Cronjob model) throws InterceptorException {
		cacheManager.getCache(CronjobConstants.Cache.CRONJOB).clear();
		cacheManager.getCache(CronjobConstants.Cache.CRONJOBS).clear();
	}

}
