package com.beanframework.core.interceptor.company;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.domain.Company;

public class CompanyValidateInterceptor extends AbstractValidateInterceptor<Company> {

	@Override
	public void onValidate(Company model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
