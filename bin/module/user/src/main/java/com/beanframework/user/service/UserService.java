package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

public interface UserService {

	User findOneEntityByUuid(UUID uuid) throws Exception;

	User findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	User findAuthenticate(String id, String password) throws Exception;

	Set<GrantedAuthority> getAuthorities(List<UserGroup> userGroups, Set<String> processedUserGroupUuids);
	
	User getCurrentUser() throws Exception;
}
