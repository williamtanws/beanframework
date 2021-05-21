package com.beanframework.user.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import com.beanframework.user.data.UserSession;
import com.beanframework.user.domain.User;

public interface UserService {

  UsernamePasswordAuthenticationToken findAuthenticate(String id, String password) throws Exception;

  UserDetails findUserDetails(String id);

  void setCurrentUser(User model);

  User getCurrentUserSession();

  User getCurrentUser() throws Exception;

  void updateCurrentUserSession() throws Exception;

  Set<UserSession> findAllSessions();

  void expireAllSessionsByUuid(UUID uuid);

  void expireAllSessions();

  void saveProfilePicture(User model, MultipartFile picture) throws IOException;

  void saveProfilePicture(User model, InputStream inputStream) throws IOException;

  void deleteProfilePictureFileByUuid(UUID uuid);

  Set<UUID> getAllUserGroupsByCurrentUser() throws Exception;

  void generateUserAttribute(User model, String configurationDynamicFieldTemplate) throws Exception;

  void loginSuccessHandler();

  void logoutSuccessHandler();
}
