package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import com.beanframework.user.domain.UserAuthority;

public interface UserAuthorityFacade {

	List<UserAuthority> findByUserGroupUuid(UUID uuid);

}
