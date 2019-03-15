package com.beanframework.menu.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuLoadInterceptor implements LoadInterceptor<Menu> {

	@Override
	public void onLoad(Menu model, InterceptorContext context) throws InterceptorException {

	}

}
