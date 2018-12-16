package com.beanframework.menu.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.menu.MenuConstants;
import com.beanframework.menu.domain.Menu;

public class MenuPrepareInterceptor implements PrepareInterceptor<Menu> {
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void onPrepare(Menu model) throws InterceptorException {
		cacheManager.getCache(MenuConstants.Cache.MENU).clear();
		cacheManager.getCache(MenuConstants.Cache.NAVIGATION_TREE).clear();
		cacheManager.getCache(MenuConstants.Cache.NAVIGATION_TREE_BY_USERGROUP).clear();
	}

}
