package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;

public class SiteLoadInterceptor extends AbstractLoadInterceptor<Site> {

	@Override
	public void onLoad(Site model, InterceptorContext context) throws InterceptorException {
		Site prototype = new Site();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setUrl(model.getUrl());

		model = prototype;
	}

}
