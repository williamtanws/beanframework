package com.beanframework.core.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.core.data.MenuDto;
import com.beanframework.core.facade.MenuFacade;

public class MenuNavigationBeanImpl implements MenuNavigationBean {

	@Autowired
	private MenuFacade menuFacade;

	@Override
	public List<MenuDto> findMenuTreeByCurrentUser() throws Exception {
		return menuFacade.findMenuTreeByCurrentUser();
	}

}
