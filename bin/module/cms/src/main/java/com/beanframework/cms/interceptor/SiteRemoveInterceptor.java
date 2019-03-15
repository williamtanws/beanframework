package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;

public class SiteRemoveInterceptor implements RemoveInterceptor<Site> {

	@Override
	public void onRemove(Site model, InterceptorContext context) throws InterceptorException {
	}

}
