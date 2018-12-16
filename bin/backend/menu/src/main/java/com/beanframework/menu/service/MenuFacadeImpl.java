package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;

@Component
public class MenuFacadeImpl implements MenuFacade {

	Logger logger = LoggerFactory.getLogger(MenuFacadeImpl.class.getName());
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private MenuService menuService;

	@Override
	public void changePosition(UUID fromUuid, UUID toUuid, int toIndex) {
		menuService.savePosition(fromUuid, toUuid, toIndex);
	}

	@Override
	public List<Menu> findMenuTree() {
		return modelService.getDto(menuService.findMenuTree());
	}

	@Override
	public List<Menu> findNavigationTreeByUserGroup(List<UUID> userGroupUuids) {
		return modelService.getDto(menuService.findNavigationTreeByUserGroup(userGroupUuids));
	}
}
