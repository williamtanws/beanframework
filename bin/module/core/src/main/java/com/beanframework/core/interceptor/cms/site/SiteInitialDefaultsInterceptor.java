package com.beanframework.core.interceptor.cms.site;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;

public class SiteInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Site> {

	@Override
	public void onInitialDefaults(Site model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
