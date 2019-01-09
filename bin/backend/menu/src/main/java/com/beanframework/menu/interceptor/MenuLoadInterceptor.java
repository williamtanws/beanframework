package com.beanframework.menu.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.menu.domain.Menu;

public class MenuLoadInterceptor implements LoadInterceptor<Menu> {

	@Override
	public void onLoad(Menu model) throws InterceptorException {

		Hibernate.initialize(model.getParent());
		Hibernate.initialize(model.getChilds());
		Hibernate.initialize(model.getUserGroups());
	}

}
