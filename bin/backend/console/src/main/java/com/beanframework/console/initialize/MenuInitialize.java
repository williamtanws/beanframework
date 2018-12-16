package com.beanframework.console.initialize;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.Initializer;
import com.beanframework.common.exception.ModelRemovalException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuFacade;

public class MenuInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(MenuInitialize.class);
	
	@Autowired
	private ModelService modelService;

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
			
			try {
				modelService.remove(menu.getUuid(), Menu.class);
			} catch (ModelRemovalException e) {
				logger.error(e.getMessage());
			}
		}
	}

}
