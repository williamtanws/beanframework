package com.beanframework.menu.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuPrepareInterceptor extends AbstractPrepareInterceptor<Menu> {

	@Override
	public void onPrepare(Menu model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

		if (StringUtils.isBlank(model.getPath())) {
			model.setPath(null);
		}
		if (StringUtils.isBlank(model.getIcon())) {
			model.setIcon(null);
		}
		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}
}
