package com.beanframework.menu.domain;

import java.util.List;

import com.beanframework.menu.domain.Menu;

public class MenuNavigation extends Menu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3562375078409723942L;

	private List<MenuNavigation> navigationChilds;

	public List<MenuNavigation> getNavigationChilds() {
		return navigationChilds;
	}

	public void setNavigationChilds(List<MenuNavigation> navigationChilds) {
		this.navigationChilds = navigationChilds;
	}

}
