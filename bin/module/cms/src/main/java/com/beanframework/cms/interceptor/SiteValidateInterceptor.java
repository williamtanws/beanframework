package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;

public class SiteValidateInterceptor extends AbstractValidateInterceptor<Site> {

	@Override
	public void onValidate(Site model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
