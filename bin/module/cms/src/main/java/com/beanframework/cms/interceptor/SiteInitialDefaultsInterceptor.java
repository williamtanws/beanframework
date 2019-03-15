package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;

public class SiteInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Site> {

	@Override
	public void onInitialDefaults(Site model, InterceptorContext context) throws InterceptorException {
	}

}
