package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;

public class SiteInitializeInterceptor implements InitializeInterceptor<Site> {

	@Override
	public void onInitialize(Site model) throws InterceptorException {
	}

}
