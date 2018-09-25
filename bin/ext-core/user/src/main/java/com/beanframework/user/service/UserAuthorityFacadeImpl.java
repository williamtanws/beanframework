package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.user.domain.UserAuthority;

@Component
public class UserAuthorityFacadeImpl implements UserAuthorityFacade {

	@Autowired
	private UserAuthorityService userAuthorityService;

	@Override
	public List<UserAuthority> findByUserGroupUuid(UUID uuid) {
		return userAuthorityService.findByUserGroupUuid(uuid);
	}
}
