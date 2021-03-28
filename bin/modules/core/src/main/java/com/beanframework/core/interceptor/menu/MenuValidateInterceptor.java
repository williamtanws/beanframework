package com.beanframework.core.interceptor.menu;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuValidateInterceptor extends AbstractValidateInterceptor<Menu> {

	@Override
	public void onValidate(Menu model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
