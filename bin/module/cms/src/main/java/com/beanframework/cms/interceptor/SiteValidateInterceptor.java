package com.beanframework.cms.interceptor;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;

public class SiteValidateInterceptor implements ValidateInterceptor<Site> {

	@Override
	public void onValidate(Site model, InterceptorContext context) throws InterceptorException {

	}
}
