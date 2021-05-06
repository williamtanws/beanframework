package com.beanframework.core.interceptor.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuService;

public class MenuLoadInterceptor extends AbstractLoadInterceptor<Menu> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MenuLoadInterceptor.class);

	@Autowired
	private MenuService menuService;

	@Override
	public void onLoad(Menu model, InterceptorContext context) throws InterceptorException {
		try {
			menuService.generateMenuField(model);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
