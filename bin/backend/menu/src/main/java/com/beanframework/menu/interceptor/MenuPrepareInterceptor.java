package com.beanframework.menu.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuPrepareInterceptor implements PrepareInterceptor<Menu> {

	@Override
	public void onPrepare(Menu model) throws InterceptorException {
	}

}
