package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;

public class SitePrepareInterceptor implements PrepareInterceptor<Site> {

	@Override
	public void onPrepare(Site model, InterceptorContext context) throws InterceptorException {

	}

}
