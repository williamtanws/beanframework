package com.beanframework.menu.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.menu.MenuConstants;
import com.beanframework.menu.domain.Menu;

public class MenuRemoveInterceptor implements RemoveInterceptor<Menu> {
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void onRemove(Menu model) throws InterceptorException {
		cacheManager.getCache(MenuConstants.Cache.MENU).clear();
		cacheManager.getCache(MenuConstants.Cache.NAVIGATION_TREE).clear();
		cacheManager.getCache(MenuConstants.Cache.NAVIGATION_TREE_BY_USERGROUP).clear();
	}

}
