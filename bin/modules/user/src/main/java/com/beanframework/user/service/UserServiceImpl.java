package com.beanframework.user.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.logentry.LogentryType;
import com.beanframework.user.UserConstants;
import com.beanframework.user.data.UserSession;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAttribute;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.event.AuthenticationEvent;

@Service
public class UserServiceImpl implements UserService {

  protected static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private ModelService modelService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PersistenceContext
  protected EntityManager entityManager;

  @Autowired
  private SessionRegistry sessionRegistry;

  @Value(UserConstants.USER_MEDIA_LOCATION)
  public String PROFILE_PICTURE_LOCATION;

  @Value(UserConstants.USER_PROFILE_PICTURE_THUMBNAIL_WIDTH)
  public int USER_PROFILE_PICTURE_THUMBNAIL_WIDTH;

  @Value(UserConstants.USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT)
  public int USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT;

  @Value(UserConstants.Admin.DEFAULT_GROUP)
  private String defaultAdminGroup;

  @Value(UserConstants.Access.CONSOLE)
  private String ACCESS_CONSOLE;

  @Value(UserConstants.Access.BACKOFFICE)
  private String ACCESS_BACKOFFICE;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  @Override
  public UsernamePasswordAuthenticationToken findAuthenticate(String id, String password)
      throws InterceptorException {

    if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
      throw new BadCredentialsException("Bad Credentials");
    }

    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(User.ID, id);

    User user = modelService.findOneByProperties(properties, User.class);

    // If account not exists in database
    if (user == null) {
      applicationEventPublisher.publishEvent(
          new AuthenticationEvent(user, LogentryType.LOGIN, "Not found for ID=" + id));
      throw new BadCredentialsException("Bad Credentials");
    }

    if (passwordEncoder.matches(password, user.getPassword()) == Boolean.FALSE) {

      if (StringUtils.isNotBlank(user.getParameters().get(UserConstants.LOGIN_ATTEMPT_COUNT))) {
        try {
          int loginAttemptCount =
              Integer.valueOf(user.getParameters().get(UserConstants.LOGIN_ATTEMPT_COUNT));
          loginAttemptCount = loginAttemptCount + 1;

          user.getParameters().put(UserConstants.LOGIN_ATTEMPT_COUNT,
              String.valueOf(loginAttemptCount));
        } catch (Exception e) {
          LOGGER.error(e.getMessage(), e);
        }
      } else {
        user.getParameters().put(UserConstants.LOGIN_ATTEMPT_COUNT, "1");
      }
      user = modelService.saveEntityByLegacyMode(user);

      applicationEventPublisher.publishEvent(
          new AuthenticationEvent(user, LogentryType.LOGIN, "Wrong password for ID=" + id));
      throw new BadCredentialsException("Bad Credentials");
    }

    if (isAdmin(user)) {
      return new UsernamePasswordAuthenticationToken(user, user.getPassword(),
          getAdminAuthorities());
    }

    user.getParameters().put(UserConstants.LOGIN_LAST_DATE,
        UserConstants.PARAMETER_DATE_FORMAT.format(new Date()));
    modelService.saveEntityByLegacyMode(user);

    // Normal user group
    if (user.getEnabled() == Boolean.FALSE) {
      throw new DisabledException("Account Disabled");
    }
    if (user.getAccountNonExpired() == Boolean.FALSE) {
      try {
        if (StringUtils.isNotBlank(user.getParameters().get(UserConstants.ACCOUNT_EXPIRED_DATE))) {
          Date expiredDate = UserConstants.PARAMETER_DATE_FORMAT
              .parse(user.getParameters().get(UserConstants.ACCOUNT_EXPIRED_DATE));

          if (new Date().after(expiredDate)) {
            throw new AccountExpiredException("Account Expired");
          }
        }
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    if (user.getAccountNonLocked() == Boolean.FALSE) {
      try {
        if (StringUtils.isNotBlank(user.getParameters().get(UserConstants.LOGIN_ATTEMPT_COUNT))
            && StringUtils.isNotBlank(user.getParameters().get(UserConstants.LOGIN_ATTEMPT_MAX))) {
          int loginAttemptCount =
              Integer.valueOf(user.getParameters().get(UserConstants.LOGIN_ATTEMPT_COUNT));
          int loginAttemptMax =
              Integer.valueOf(user.getParameters().get(UserConstants.LOGIN_ATTEMPT_MAX));

          if (loginAttemptCount >= loginAttemptMax) {
            throw new LockedException("Account Locked");
          }
        }
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    if (user.getCredentialsNonExpired() == Boolean.FALSE) {
      try {
        if (user.getParameters().get(UserConstants.PASSWORD_EXPIRED_DATE) != null) {
          Date expiredDate = UserConstants.PARAMETER_DATE_FORMAT
              .parse(user.getParameters().get(UserConstants.PASSWORD_EXPIRED_DATE));

          if (new Date().after(expiredDate)) {
            throw new CredentialsExpiredException("Password Expired");
          }
        }
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }

    return new UsernamePasswordAuthenticationToken(user, user.getPassword(),
        getUserAuthorities(user));
  }

  private boolean isAdmin(User user) throws InterceptorException {
    // Find admin
    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(UserGroup.ID, defaultAdminGroup);

    UserGroup adminGroup = modelService.findOneByProperties(properties, UserGroup.class);

    // Check admin user group
    if (adminGroup != null) {
      for (UUID userGroupUuid : user.getUserGroups()) {
        if (userGroupUuid.equals(adminGroup.getUuid())) {
          return true;
        }

        // Check user group's sub group
        UserGroup userGroup = modelService.findOneByUuid(userGroupUuid, UserGroup.class);
        if (userGroup != null) {
          for (UUID subGroupUuid : userGroup.getUserGroups()) {
            if (subGroupUuid.equals(adminGroup.getUuid())) {
              return true;
            }
          }
        }
      }
    }

    return false;
  }

  private Set<GrantedAuthority> getAdminAuthorities() {

    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority(ACCESS_CONSOLE));
    authorities.add(new SimpleGrantedAuthority(ACCESS_BACKOFFICE));

    List<UserRight> userRights = modelService.findAll(UserRight.class);
    List<UserPermission> userPermissions = modelService.findAll(UserPermission.class);

    for (UserPermission userPermission : userPermissions) {
      for (UserRight userRight : userRights) {
        authorities
            .add(new SimpleGrantedAuthority(userPermission.getId() + "_" + userRight.getId()));
      }
    }

    return authorities;
  }

  private Set<GrantedAuthority> getUserAuthorities(User model) throws InterceptorException {

    // Find employee user group and sub user group authority
    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

    // User Group
    for (UUID userGroupUuid : model.getUserGroups()) {
      UserGroup userGroup = modelService.findOneByUuid(userGroupUuid, UserGroup.class);

      Hibernate.initialize(userGroup.getUserAuthorities());
      authorities.addAll(getGrantedAuthority(userGroup.getUserAuthorities()));

      // Sub User Group
      for (UUID subGroupUuid : userGroup.getUserGroups()) {

        UserGroup subGroup = modelService.findOneByUuid(subGroupUuid, UserGroup.class);
        if (subGroup != null) {
          Hibernate.initialize(subGroup.getUserAuthorities());
          authorities.addAll(getGrantedAuthority(subGroup.getUserAuthorities()));
        }
      }
    }

    return authorities;
  }

  private Set<GrantedAuthority> getGrantedAuthority(List<UserAuthority> userAuthorities) {
    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

    for (UserAuthority userAuthority : userAuthorities) {

      if (Boolean.TRUE.equals(userAuthority.getEnabled())) {
        StringBuilder authority = new StringBuilder();

        authority.append(userAuthority.getUserPermission().getId());
        authority.append("_");
        authority.append(userAuthority.getUserRight().getId());

        authorities.add(new SimpleGrantedAuthority(authority.toString()));
      }
    }

    return authorities;
  }

  @Override
  public UserDetails findUserDetails(String id) {
    try {
      Map<String, Object> properties = new HashMap<String, Object>();
      properties.put(User.ID, id);

      User user = modelService.findOneByProperties(properties, User.class);

      UserDetails userDetails = new UserDetails() {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isEnabled() {
          return user.getEnabled();
        }

        @Override
        public boolean isCredentialsNonExpired() {
          return user.getCredentialsNonExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
          return user.getAccountNonLocked();
        }

        @Override
        public boolean isAccountNonExpired() {
          return user.getAccountNonExpired();
        }

        @Override
        public String getUsername() {
          return user.getId();
        }

        @Override
        public String getPassword() {
          return user.getPassword();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
          try {
            if (isAdmin(user)) {
              return getAdminAuthorities();
            } else {
              return getUserAuthorities(user);
            }
          } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
          }
        }
      };

      return userDetails;

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      return null;
    }
  }

  @Override
  public Set<UserSession> findAllSessions() {
    final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
    Set<UserSession> sessions = new LinkedHashSet<UserSession>();

    for (final Object principal : allPrincipals) {
      if (principal instanceof User) {
        final User user = (User) principal;
        if (user.getUuid() != null) {
          List<SessionInformation> sessionInformations =
              sessionRegistry.getAllSessions(user, false);
          sessions.add(new UserSession(user, sessionInformations));
        }
      }
    }
    return sessions;
  }

  @Override
  public void expireAllSessionsByUuid(UUID uuid) {
    Set<UserSession> userList = findAllSessions();

    for (UserSession userSession : userList) {
      if (userSession.getUser().getUuid().equals(uuid)) {
        List<SessionInformation> session =
            sessionRegistry.getAllSessions(userSession.getUser(), false);
        for (SessionInformation sessionInformation : session) {
          sessionInformation.expireNow();
        }
      }
    }
  }

  @Override
  public void expireAllSessions() {
    Set<UserSession> userList = findAllSessions();

    for (UserSession userSession : userList) {
      List<SessionInformation> session =
          sessionRegistry.getAllSessions(userSession.getUser(), false);
      for (SessionInformation sessionInformation : session) {
        sessionInformation.expireNow();
      }
    }
  }

  @Override
  public void setCurrentUser(User model) {
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(model, model.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Override
  public User getCurrentUserSession() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null) {

      User principal = (User) auth.getPrincipal();
      return principal;
    } else {
      return null;
    }
  }

  @Override
  public User getCurrentUser() throws InterceptorException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null) {

      User principal = (User) auth.getPrincipal();
      return modelService.findOneByUuid(principal.getUuid(), User.class);
    } else {
      return null;
    }
  }

  @Override
  public void updateCurrentUserSession() throws InterceptorException {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User principal = (User) auth.getPrincipal();

    User user = modelService.findOneByUuid(principal.getUuid(), User.class);

    UsernamePasswordAuthenticationToken token = null;
    if (isAdmin(user)) {
      token =
          new UsernamePasswordAuthenticationToken(user, user.getPassword(), getAdminAuthorities());
      SecurityContextHolder.getContext().setAuthentication(token);
    } else {
      token = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
          getUserAuthorities(user));
    }
    SecurityContextHolder.getContext().setAuthentication(token);
  }

  @Override
  public void saveProfilePicture(User model, MultipartFile picture) throws IOException {
    if (picture != null && picture.isEmpty() == Boolean.FALSE) {

      File profilePictureFolder = new File(PROFILE_PICTURE_LOCATION, model.getUuid().toString());
      FileUtils.forceMkdir(profilePictureFolder);

      File original =
          new File(PROFILE_PICTURE_LOCATION, model.getUuid() + File.separator + "original.png");
      original = new File(original.getAbsolutePath());
      picture.transferTo(original);

      File thumbnail =
          new File(PROFILE_PICTURE_LOCATION, model.getUuid() + File.separator + "thumbnail.png");
      BufferedImage img = ImageIO.read(original);
      BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC,
          USER_PROFILE_PICTURE_THUMBNAIL_WIDTH, USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT,
          Scalr.OP_ANTIALIAS);
      ImageIO.write(thumbImg, "png", thumbnail);
    }
  }

  @Override
  public void saveProfilePicture(User customer, InputStream inputStream) throws IOException {

    File profilePictureFolder = new File(PROFILE_PICTURE_LOCATION, customer.getUuid().toString());
    FileUtils.forceMkdir(profilePictureFolder);

    File original =
        new File(PROFILE_PICTURE_LOCATION, customer.getUuid() + File.separator + "original.png");
    original = new File(original.getAbsolutePath());
    FileUtils.copyInputStreamToFile(inputStream, original);

    File thumbnail =
        new File(PROFILE_PICTURE_LOCATION, customer.getUuid() + File.separator + "thumbnail.png");
    BufferedImage img = ImageIO.read(original);
    BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC,
        USER_PROFILE_PICTURE_THUMBNAIL_WIDTH, USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT,
        Scalr.OP_ANTIALIAS);
    ImageIO.write(thumbImg, "png", thumbnail);

  }

  @Override
  public void deleteProfilePictureFileByUuid(UUID uuid) {
    File profilePictureFolder = new File(PROFILE_PICTURE_LOCATION + File.separator + uuid);
    try {
      if (profilePictureFolder.exists()) {
        FileUtils.deleteDirectory(profilePictureFolder);
      }
    } catch (IOException e) {
      LOGGER.error(e.toString(), e);
    }
  }

  @Transactional
  @Override
  public Set<UUID> getAllUserGroupsByCurrentUser() throws InterceptorException {
    Set<UUID> userGroupUuids = new HashSet<UUID>();

    User user = getCurrentUserSession();

    for (UUID userGroupUuid : user.getUserGroups()) {

      UserGroup userGroup = modelService.findOneByUuid(userGroupUuid, UserGroup.class);

      // Check if user in admin group
      if (userGroup.getId().equals(defaultAdminGroup)) {
        List<UserGroup> allUserGroups = modelService.findAll(UserGroup.class);
        for (UserGroup allUserGroup : allUserGroups) {
          userGroupUuids.add(allUserGroup.getUuid());
        }
      } else {
        if (userGroup != null) {
          userGroupUuids.add(userGroup.getUuid());
        }

        for (UUID subUserGroupUuid : userGroup.getUserGroups()) {
          UserGroup subUserGroup = modelService.findOneByUuid(subUserGroupUuid, UserGroup.class);

          // Check if user in sub admin group
          if (userGroup.getId().equals(defaultAdminGroup)) {
            List<UserGroup> allUserGroups = modelService.findAll(UserGroup.class);
            for (UserGroup allUserGroup : allUserGroups) {
              userGroupUuids.add(allUserGroup.getUuid());
            }
          } else {
            userGroupUuids.add(subUserGroup.getUuid());
          }
        }
      }
    }

    return userGroupUuids;
  }

  @Override
  public void generateUserAttribute(User model, String configurationDynamicFieldTemplate)
      throws Exception {
    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Configuration.ID, configurationDynamicFieldTemplate);
    Configuration configuration = modelService.findOneByProperties(properties, Configuration.class);

    if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
      properties = new HashMap<String, Object>();
      properties.put(DynamicFieldTemplate.ID, configuration.getValue());

      DynamicFieldTemplate dynamicFieldTemplate =
          modelService.findOneByProperties(properties, DynamicFieldTemplate.class);

      if (dynamicFieldTemplate != null) {

        for (UUID dynamicFieldSlot : dynamicFieldTemplate.getDynamicFieldSlots()) {

          boolean addField = true;
          for (UserAttribute field : model.getAttributes()) {
            if (field.getDynamicFieldSlot().equals(dynamicFieldSlot)) {
              addField = false;
            }
          }

          if (addField) {
            UserAttribute field = new UserAttribute();
            field.setDynamicFieldSlot(dynamicFieldSlot);
            field.setUser(model);
            model.getAttributes().add(field);
          }
        }
      }
    }
  }

  @Override
  public void loginSuccessHandler() {
    try {
      User user = getCurrentUser();

      applicationEventPublisher
          .publishEvent(new AuthenticationEvent(user, LogentryType.LOGIN, "ID=" + user.getId()));
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Override
  public void logoutSuccessHandler() {
    try {
      User user = getCurrentUser();
      user.getParameters().put(UserConstants.LOGOUT_LAST_DATE,
          UserConstants.PARAMETER_DATE_FORMAT.format(new Date()));
      modelService.saveEntityByLegacyMode(user);

      applicationEventPublisher
          .publishEvent(new AuthenticationEvent(user, LogentryType.LOGOUT, "ID=" + user.getId()));
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
