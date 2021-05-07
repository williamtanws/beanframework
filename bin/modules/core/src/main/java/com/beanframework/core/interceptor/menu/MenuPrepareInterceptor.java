package com.beanframework.core.interceptor.menu;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuPrepareInterceptor extends AbstractPrepareInterceptor<Menu> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(MenuPrepareInterceptor.class);

	@Override
	public void onPrepare(Menu model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

		if (StringUtils.isBlank(model.getPath())) {
			model.setPath(null);
		}
		if (StringUtils.isBlank(model.getIcon())) {
			model.setIcon(null);
		}
		for (int i = 0; i < model.getAttributes().size(); i++) {
			if (StringUtils.isBlank(model.getAttributes().get(i).getValue())) {
				model.getAttributes().get(i).setValue(null);
			}
		}
	}
}
