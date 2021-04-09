package com.beanframework.core.interceptor.menu;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuLoadInterceptor extends AbstractLoadInterceptor<Menu> {

	@Override
	public void onLoad(Menu model, InterceptorContext context) throws InterceptorException {
	}
}