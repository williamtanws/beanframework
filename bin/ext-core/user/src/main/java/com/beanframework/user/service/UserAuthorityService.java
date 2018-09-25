package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.user.domain.UserAuthority;

public interface UserAuthorityService {
	
	UserAuthority create();

	List<UserAuthority> findByUserGroupUuid(UUID uuid);

	void deleteUserRightByUserRightUuid(UUID uuid);

	void deleteUserRightByUserPermissionUuid(UUID uuid);

}
