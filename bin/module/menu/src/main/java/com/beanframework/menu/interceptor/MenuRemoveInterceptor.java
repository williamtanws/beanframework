package com.beanframework.menu.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuRemoveInterceptor implements RemoveInterceptor<Menu> {

	@Override
	public void onRemove(Menu model, InterceptorContext context) throws InterceptorException {
	}

}
