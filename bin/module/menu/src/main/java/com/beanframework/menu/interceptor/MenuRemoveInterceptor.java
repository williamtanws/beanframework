package com.beanframework.menu.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuRemoveInterceptor extends AbstractRemoveInterceptor<Menu> {

	@Override
	public void onRemove(Menu model, InterceptorContext context) throws InterceptorException {
		super.onRemove(model, context);
	}

}
