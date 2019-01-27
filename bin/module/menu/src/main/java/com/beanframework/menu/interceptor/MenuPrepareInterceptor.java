package com.beanframework.menu.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuPrepareInterceptor implements PrepareInterceptor<Menu> {

	@Override
	public void onPrepare(Menu model) throws InterceptorException {
		
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
