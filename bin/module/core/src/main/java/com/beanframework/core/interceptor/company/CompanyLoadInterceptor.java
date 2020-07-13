package com.beanframework.core.interceptor.company;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.company.domain.Company;

public class CompanyLoadInterceptor extends AbstractLoadInterceptor<Company> {

	@Override
	public void onLoad(Company model, InterceptorContext context) throws InterceptorException {
	}

}
