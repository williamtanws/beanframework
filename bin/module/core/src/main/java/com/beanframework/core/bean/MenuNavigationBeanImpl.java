package com.beanframework.core.bean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.facade.EmployeeFacade;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuService;

public class MenuNavigationBeanImpl implements MenuNavigationBean {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private EmployeeFacade employeeFacade;

	@Autowired
	private ModelService modelService;

	@Override
	public List<MenuDto> findMenuTreeByCurrentUser() throws Exception {

		List<Menu> entities = menuService.findEntityMenuTree(true);
		List<MenuDto> menuDtoTree = modelService.getDto(entities, MenuDto.class);

		EmployeeDto employee = employeeFacade.getCurrentUser();

		filterAuthorizedMenu(menuDtoTree, collectUserGroupDtoUuid(employee.getUserGroups()));

		return menuDtoTree;
	}

	private Set<String> collectUserGroupDtoUuid(List<UserGroupDto> userGroups) {
		Set<String> userGroupUuids = new HashSet<String>();
		for (UserGroupDto userGroup : userGroups) {
			userGroupUuids.add(userGroup.getUuid().toString());
			if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false) {
				userGroupUuids.addAll(collectUserGroupDtoUuid(userGroup.getUserGroups()));
			}
		}
		return userGroupUuids;
	}

	private void filterAuthorizedMenu(List<MenuDto> menuRootList, Set<String> authorizedUserGroupUuidList) {
		Iterator<MenuDto> parent = menuRootList.iterator();
		while (parent.hasNext()) {
			MenuDto menu = parent.next();
			if (menu.getEnabled() == false) {
				parent.remove();
			}

			// If menu groups or authorities is not authorized
			if (isUserGroupAuthorized(menu, authorizedUserGroupUuidList) == false) {
				parent.remove();
			}
			if (isAnyUserAuthorityAuthorized(menu.getUserGroups()) == false) {
				parent.remove();
			}

			// If menu has childs
			if (menu.getChilds().isEmpty() == false) {
				filterAuthorizedMenu(menu.getChilds(), authorizedUserGroupUuidList);
			}
		}
	}

	private boolean isUserGroupAuthorized(MenuDto menu, Set<String> authorizedUserGroupUuidList) {
		Set<String> menuUserGroupUuidList = collectUserGroupDtoUuid(menu.getUserGroups());
		for (String menuUserGroupUuid : menuUserGroupUuidList) {
			for (String authorizedUserGroupUuid : authorizedUserGroupUuidList) {
				if (authorizedUserGroupUuid.equals(menuUserGroupUuid)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean isAnyUserAuthorityAuthorized(List<UserGroupDto> userGroups) {
		boolean authorized = false;

		for (UserGroupDto userGroup : userGroups) {

			authorized = isUserGroupAuthoritiesAuthorized(userGroup);

			if (authorized == false) {
				if (userGroup.getUserGroups().isEmpty() == false) {
					authorized = isAnyUserAuthorityAuthorized(userGroup.getUserGroups());
				}
			}
		}

		return authorized;
	}

	private boolean isUserGroupAuthoritiesAuthorized(UserGroupDto userGroup) {
		for (UserAuthorityDto userAuthority : userGroup.getUserAuthorities()) {
			if (userAuthority.getEnabled().equals(Boolean.TRUE)) {
				return true;
			}
		}

		return false;
	}

}
