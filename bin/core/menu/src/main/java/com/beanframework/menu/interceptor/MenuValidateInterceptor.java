package com.beanframework.menu.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuValidateInterceptor implements ValidateInterceptor<Menu> {

	@Override
	public void onValidate(Menu model) throws InterceptorException {
	
	}

}
