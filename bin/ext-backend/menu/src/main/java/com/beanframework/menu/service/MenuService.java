package com.beanframework.menu.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.beanframework.menu.domain.Menu;

public interface MenuService {

	Menu create();

	Menu initDefaults(Menu menu);

	Menu save(Menu menu);

	List<Menu> save(List<Menu> menus);

	void savePosition(UUID fromUuid, UUID toUuid, int toIndex);

	void delete(UUID uuid);

	void deleteAll();

	Optional<Menu> findEntityByUuid(UUID uuid);

	Optional<Menu> findEntityById(String id);

	Menu findByUuid(UUID uuid);

	Menu findById(String id);

	Menu findByPath(String path);

	List<Menu> findMenuTree();

	List<Menu> findNavigationTreeByUserGroup(List<UUID> userGroupUuids);
}
