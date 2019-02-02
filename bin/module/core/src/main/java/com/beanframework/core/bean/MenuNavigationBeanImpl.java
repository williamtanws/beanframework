package com.beanframework.core.bean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuService;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

public class MenuNavigationBeanImpl implements MenuNavigationBean{
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ModelService modelService;
	
	@Override
	public List<MenuDto> findMenuTreeByCurrentUser() throws Exception {
		List<Menu> entities = menuService.findEntityMenuTree(true);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		List<MenuDto> menuDtoTree = modelService.getDto(entities, context, MenuDto.class);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();

		filterAuthorizedMenu(menuDtoTree, collectUserGroupUuid(user.getUserGroups()));
		
		return menuDtoTree;
	}
	
	private Set<String> collectUserGroupUuid(List<UserGroup> userGroups) {
		Set<String> userGroupUuids = new HashSet<String>();
		for (UserGroup userGroup : userGroups) {
			userGroupUuids.add(userGroup.getUuid().toString());
			if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false) {
				userGroupUuids.addAll(collectUserGroupUuid(userGroup.getUserGroups()));
			}
		}
		return userGroupUuids;
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
			if (isUserGroupAuthorized(menu, authorizedUserGroupUuidList) == false || isAnyUserAuthorityAuthorized(menu.getUserGroups()) == false) {
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
