package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;

public class SiteLoadInterceptor implements LoadInterceptor<Site> {

	@Override
	public void onLoad(Site model) throws InterceptorException {
	}

}
