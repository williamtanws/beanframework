package com.beanframework.console.initialize;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.MapBindingResult;

import com.beanframework.common.Initializer;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuFacade;

public class MenuInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(MenuInitialize.class);

	@Autowired
	private MenuFacade menuFacade;

	@PostConstruct
	public void initializer() {
		setKey(WebPlatformConstants.Initialize.Menu.KEY);
		setName(WebPlatformConstants.Initialize.Menu.NAME);
		setSort(WebPlatformConstants.Initialize.Menu.SORT);
		setDescription(WebPlatformConstants.Initialize.Menu.DESCRIPTION);
	}

	@Override
	public void initialize() {
		List<Menu> menuList = menuFacade.findMenuTree();
		for (Menu menu : menuList) {
			MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(),
					Menu.class.getName());
			menuFacade.delete(menu.getUuid(), bindingResult);
		}
	}

}
