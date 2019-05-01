package com.beanframework.core.bean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuService;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserService;

public class MenuNavigationBeanImpl implements MenuNavigationBean {

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelService modelService;

	@Override
	public List<MenuDto> findMenuTreeByCurrentUser() throws Exception {

		List<Menu> entities = menuService.findMenuTree(true);
		List<MenuDto> menuDtoTree = modelService.getDto(entities, MenuDto.class, new DtoConverterContext(ConvertRelationType.ALL));

		List<UserGroup> userGroups = userService.getUserGroupsByCurrentUser();
		filterAuthorizedMenu(menuDtoTree, userGroups);

		return menuDtoTree;
	}

	private Set<String> collectUserGroupUuid(List<UserGroup> userGroups) {
		Set<String> userGroupUuids = new HashSet<String>();
		for (UserGroup userGroup : userGroups) {
			if (userGroupUuids.contains(userGroup.getUuid().toString()) == false) {
				userGroupUuids.add(userGroup.getUuid().toString());
				if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false) {
					userGroupUuids.addAll(collectUserGroupUuid(userGroup.getUserGroups()));
				}
			}
		}
		return userGroupUuids;
	}

	private Set<String> collectUserGroupDtoUuid(List<UserGroupDto> userGroups) {
		Set<String> userGroupUuids = new HashSet<String>();
		for (UserGroupDto userGroup : userGroups) {
			if (userGroupUuids.contains(userGroup.getUuid().toString()) == false) {
				userGroupUuids.add(userGroup.getUuid().toString());
				if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false) {
					userGroupUuids.addAll(collectUserGroupDtoUuid(userGroup.getUserGroups()));
				}
			}
		}
		return userGroupUuids;
	}

	private void filterAuthorizedMenu(List<MenuDto> menuRootList, List<UserGroup> userGroups) {
		Iterator<MenuDto> parent = menuRootList.iterator();
		while (parent.hasNext()) {
			MenuDto menu = parent.next();

			boolean removed = false;

			if (removed == false && menu.getEnabled() == false) {
				parent.remove();
				removed = true;
			}

			// If menu groups or authorities is not authorized
			if (removed == false && isUserGroupAuthorized(menu, userGroups) == false) {
				parent.remove();
				removed = true;
			}

			// If menu has childs
			if (menu.getChilds().isEmpty() == false) {
				filterAuthorizedMenu(menu.getChilds(), userGroups);
			}
		}
	}

	private boolean isUserGroupAuthorized(MenuDto menu, List<UserGroup> userGroups) {
		
		Set<String> collectionUserGroupUuid = collectUserGroupUuid(userGroups);
		
		Set<String> menuUserGroupUuidList = collectUserGroupDtoUuid(menu.getUserGroups());
		for (String menuUserGroupUuid : menuUserGroupUuidList) {
			for (String authorizedUserGroupUuid : collectionUserGroupUuid) {
				if (authorizedUserGroupUuid.equals(menuUserGroupUuid)) {
					return true;
				}
			}
		}

		return false;
	}

}
