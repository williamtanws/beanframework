package com.beanframework.backoffice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.user.service.UserAuthorityService;
import com.beanframework.user.service.UserGroupService;
import com.beanframework.user.service.UserPermissionService;
import com.beanframework.user.service.UserRightService;

@Service
public class BackofficeModuleServiceImpl implements BackofficeModuleService {

	@Autowired
	private UserRightService userRightService;

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private UserAuthorityService userAuthorityService;

	@Override
	public void deleteAllModuleLanguageByLanguageUuid(UUID uuid) {
		userRightService.deleteLanguageByLanguageUuid(uuid);
		userPermissionService.deleteLanguageByLanguageUuid(uuid);
		userGroupService.deleteLanguageByLanguageUuid(uuid);
	}
	
	@Override
	public void deleteAllModuleUserRightByUserRightUuid(UUID uuid) {
		userAuthorityService.deleteUserRightByUserRightUuid(uuid);
	}

	@Override
	public void deleteAllModuleUserPermissionByUserPermissionUuid(UUID uuid) {
		userAuthorityService.deleteUserRightByUserPermissionUuid(uuid);
	}

	public UserRightService getUserRightService() {
		return userRightService;
	}

	public void setUserRightService(UserRightService userRightService) {
		this.userRightService = userRightService;
	}

	public UserPermissionService getUserPermissionService() {
		return userPermissionService;
	}

	public void setUserPermissionService(UserPermissionService userPermissionService) {
		this.userPermissionService = userPermissionService;
	}

	public UserGroupService getUserGroupService() {
		return userGroupService;
	}

	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

}
