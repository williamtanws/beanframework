package com.beanframework.core.interceptor.company;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.user.domain.Company;

public class CompanyPrepareInterceptor extends AbstractPrepareInterceptor<Company> {

	@Override
	public void onPrepare(Company model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
	}

}
