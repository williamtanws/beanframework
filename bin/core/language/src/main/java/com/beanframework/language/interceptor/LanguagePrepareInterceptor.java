package com.beanframework.language.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.language.LanguageConstants;
import com.beanframework.language.domain.Language;

public class LanguagePrepareInterceptor implements PrepareInterceptor<Language> {
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void onPrepare(Language model) throws InterceptorException {
		cacheManager.getCache(LanguageConstants.Cache.LANGUAGE).clear();
		cacheManager.getCache(LanguageConstants.Cache.LANGUAGES).clear();
	}

}
