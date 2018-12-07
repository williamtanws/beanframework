package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.validator.DeleteMenuValidator;
import com.beanframework.menu.validator.SaveMenuValidator;

@Component
public class MenuFacadeImpl implements MenuFacade {

	Logger logger = LoggerFactory.getLogger(MenuFacadeImpl.class.getName());

	@Autowired
	private MenuService menuService;

	@Autowired
	private SaveMenuValidator saveMenuValidator;

	@Autowired
	private DeleteMenuValidator deleteMenuValidator;

	@Override
	public Menu create() {
		return menuService.create();
	}

	@Override
	public Menu initDefaults(Menu menu) {
		return menuService.initDefaults(menu);
	}

	@Override
	public Menu save(Menu menu, Errors bindingResult) {
		saveMenuValidator.validate(menu, bindingResult);

		if (bindingResult.hasErrors()) {
			return menu;
		}

		return menuService.save(menu);
	}
	
	@Override
	public List<Menu> save(List<Menu> menus, MapBindingResult bindingResult) {
		saveMenuValidator.validate(menus, bindingResult);

		if (bindingResult.hasErrors()) {
			return menus;
		}

		return menuService.save(menus);
	}

	@Override
	public void changePosition(UUID fromUuid, UUID toUuid, int toIndex, BindingResult bindingResult) {
		if (fromUuid == null) {
			bindingResult.reject("fromId", "From Id required.");
		}

		if (fromUuid == null) {
			bindingResult.reject("toIndex", "To Index required.");
		}

		menuService.savePosition(fromUuid, toUuid, toIndex);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteMenuValidator.validate(uuid, bindingResult);

		if (bindingResult.hasErrors() == false) {
			menuService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		menuService.deleteAll();
	}

	@Override
	public Menu findByUuid(UUID uuid) {
		return menuService.findByUuid(uuid);
	}

	@Override
	public Menu findById(String id) {
		return menuService.findById(id);
	}

	@Override
	public Menu findByPath(String path) {
		return menuService.findByPath(path);
	}

	@Override
	public List<Menu> findMenuTree() {
		return menuService.findMenuTree();
	}

	@Override
	public List<Menu> findNavigationTreeByUserGroup(List<UUID> userGroupUuids) {
		return menuService.findNavigationTreeByUserGroup(userGroupUuids);
	}
}
