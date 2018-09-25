package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import com.beanframework.menu.domain.Menu;

public interface MenuFacade {

	Menu create();

	Menu initDefaults(Menu menu);

	Menu save(Menu menu, Errors bindingResult);
	
	List<Menu> save(List<Menu> menus, MapBindingResult bindingResult);
	
	void changePosition(UUID fromUuid, UUID toUuid, int toIndex, BindingResult bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	Menu findByUuid(UUID uuid);

	Menu findById(String id);

	Menu findByPath(String path);

	List<Menu> findMenuTree();

	List<Menu> findNavigationTreeByUserGroup(List<UUID> userGroupUuids);

}
