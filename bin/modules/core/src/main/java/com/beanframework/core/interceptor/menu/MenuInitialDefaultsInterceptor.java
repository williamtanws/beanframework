package com.beanframework.core.interceptor.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuTargetTypeEnum;
import com.beanframework.menu.service.MenuService;

public class MenuInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Menu> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MenuInitialDefaultsInterceptor.class);

	@Autowired
	private MenuService menuService;

	@Override
	public void onInitialDefaults(Menu model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);

		try {
			model.setTarget(MenuTargetTypeEnum.SELF);

			menuService.generateMenuFieldsOnInitialDefault(model);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
