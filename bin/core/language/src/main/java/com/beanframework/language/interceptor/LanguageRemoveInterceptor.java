package com.beanframework.language.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.language.LanguageConstants;
import com.beanframework.language.domain.Language;

public class LanguageRemoveInterceptor implements RemoveInterceptor<Language> {
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void onRemove(Language model) throws InterceptorException {
		cacheManager.getCache(LanguageConstants.Cache.LANGUAGE).clear();
		cacheManager.getCache(LanguageConstants.Cache.LANGUAGES).clear();
	}

}
