package com.beanframework.menu.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Menu> {

	@Override
	public void onInitialDefaults(Menu model) throws InterceptorException {
		model.setEnabled(true);
		model.setSort(-1);
	}

}
