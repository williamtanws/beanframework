package com.beanframework.backoffice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserRight;
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
	private ModelService modelService;

	@Override
	public void deleteAllModuleLanguageByLanguageUuid(UUID uuid) {
		userRightService.deleteLanguageByLanguageUuid(uuid);
		userPermissionService.deleteLanguageByLanguageUuid(uuid);
		userGroupService.deleteLanguageByLanguageUuid(uuid);
	}
	
	@Override
	public void deleteAllModuleUserRightByUserRightUuid(UUID uuid) {
		
		modelService.remove(uuid, UserAuthority.class);
	}

	@Override
	public void deleteAllModuleUserPermissionByUserPermissionUuid(UUID uuid) {		
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(UserAuthority.USER_PERMISSION_UUID, uuid);
		
		List<UserAuthority> userAuthorities = modelService.findDtoByProperties(properties, UserAuthority.class);
		modelService.remove(userAuthorities);
	}

}
