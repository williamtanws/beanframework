package com.beanframework.user.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

public interface UserService {

	User findAuthenticate(String id, String password) throws Exception;
	
	User getCurrentUser() throws Exception;

	void saveProfilePicture(User model, MultipartFile picture) throws IOException;

	void saveProfilePicture(User model, InputStream inputStream) throws IOException;

	void deleteProfilePictureByUuid(UUID uuid);
	
	Set<GrantedAuthority> getAuthorities(UUID userUuid, String userGroupId) throws Exception;

	List<UserGroup> getUserGroupsByCurrentUser() throws Exception;

	Set<String> getAllUserGroupUuidsByCurrentUser() throws Exception;

	Set<String> getAllUserGroupUuidsByUserUuid(UUID uuid) throws Exception;

	Set<String> getAllUserGroupIdsByUserUuid(UUID uuid) throws Exception;
	
	List<User> getAllUsersByUserGroupUuid(UUID uuid) throws Exception;
}
