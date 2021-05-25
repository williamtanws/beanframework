package com.beanframework.user.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.user.data.UserSession;
import com.beanframework.user.domain.User;

public interface UserService {

  UsernamePasswordAuthenticationToken findAuthenticate(String id, String password)
      throws InterceptorException;

  UserDetails findUserDetails(String id);

  void setCurrentUser(User model);

  User getCurrentUserSession();

  User getCurrentUser() throws InterceptorException;

  void updateCurrentUserSession() throws InterceptorException;

  Set<UserSession> findAllSessions();

  void expireAllSessionsByUuid(UUID uuid);

  void expireAllSessions();

  void saveProfilePicture(User model, MultipartFile picture) throws IOException;

  void saveProfilePicture(User model, InputStream inputStream) throws IOException;

  void deleteProfilePictureFileByUuid(UUID uuid);

  Set<UUID> getAllUserGroupsByUser(User model) throws InterceptorException;

  void generateUserAttribute(User model, String configurationDynamicFieldTemplate) throws Exception;

  void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response);

  void logoutSuccessHandler(HttpServletRequest request, HttpServletResponse response);
}
