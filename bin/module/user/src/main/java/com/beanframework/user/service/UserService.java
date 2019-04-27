package com.beanframework.user.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

public interface UserService {

	User findOneEntityByUuid(UUID uuid) throws Exception;

	User findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	User findAuthenticate(String id, String password) throws Exception;
	
	User getCurrentUser() throws Exception;

	void saveProfilePicture(User model, MultipartFile picture) throws IOException;

	void saveProfilePicture(User model, InputStream inputStream) throws IOException;

	void deleteProfilePictureByUuid(UUID uuid);
	
	Set<GrantedAuthority> getAuthorities(UUID userUuid, String userGroupId) throws Exception;

	List<UserGroup> getUserGroupsByCurrentUser() throws Exception;
}
