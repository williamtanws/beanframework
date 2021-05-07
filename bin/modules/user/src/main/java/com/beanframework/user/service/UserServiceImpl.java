package com.beanframework.user.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.user.UserConstants;
import com.beanframework.user.data.UserSession;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserAttribute;
import com.beanframework.user.domain.UserGroup;

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

	@Value(UserConstants.Employee.DEFAULT_GROUP)
	private String defaultEmployeeGroup;

	@Value(UserConstants.Access.CONSOLE)
	private String ACCESS_CONSOLE;

	@Value(UserConstants.Access.BACKOFFICE)
	private String ACCESS_BACKOFFICE;

	@Transactional
	@Override
	public UsernamePasswordAuthenticationToken findAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Bad Credentials");
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(User.ID, id);

		User user = modelService.findOneByProperties(properties, User.class);

		// If account not exists in database
		if (user == null) {
			throw new BadCredentialsException("Bad Credentials");
		}

		if (passwordEncoder.matches(password, user.getPassword()) == Boolean.FALSE) {
			throw new BadCredentialsException("Bad Credentials");
		}

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), getAuthorities(user));

		// Skip account validation for admin group
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(defaultAdminGroup))) {
			return authentication;
		} else {
			if (user.getEnabled() == Boolean.FALSE) {
				throw new DisabledException("Account Disabled");
			}
			if (user.getAccountNonExpired() == Boolean.FALSE) {
				throw new AccountExpiredException("Account Expired");
			}
			if (user.getAccountNonLocked() == Boolean.FALSE) {
				throw new LockedException("Account Locked");
			}
			if (user.getCredentialsNonExpired() == Boolean.FALSE) {
				throw new CredentialsExpiredException("Password Expired");
			}

			return new UsernamePasswordAuthenticationToken(user, user.getPassword(), getAuthorities(user));
		}
	}

	private Set<GrantedAuthority> getAdminAuthority() {

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		// Always add admin group in case not exists in database
		authorities.add(new SimpleGrantedAuthority(ACCESS_CONSOLE));
		authorities.add(new SimpleGrantedAuthority(ACCESS_BACKOFFICE));
		authorities.add(new SimpleGrantedAuthority(defaultAdminGroup));
		authorities.add(new SimpleGrantedAuthority(defaultAdminGroup));

		List<UserGroup> allUserGroups = modelService.findAll(UserGroup.class);
		for (UserGroup userGroup : allUserGroups) {

			Hibernate.initialize(userGroup.getUserAuthorities());
			authorities.addAll(getGrantedAuthority(userGroup.getUserAuthorities()));
		}

		return authorities;
	}

	private Set<GrantedAuthority> getAuthorities(User model) throws Exception {

		try {

			List<UserGroup> userGroups = new ArrayList<UserGroup>();
			for (UUID uuid : model.getUserGroups()) {

				UserGroup userGroup = modelService.findOneByUuid(uuid, UserGroup.class);
				if (userGroup != null) {
					userGroups.add(userGroup);
				}
			}

			// Find admin group
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserGroup.ID, defaultAdminGroup);

			UserGroup adminGroup = modelService.findOneByProperties(properties, UserGroup.class);

			// If admin group is exists
			if (adminGroup != null) {

				// Check if employee user groups contains admingroup
				for (UserGroup employeeUserGroup : userGroups) {

					if (employeeUserGroup.getUuid().equals(adminGroup.getUuid())) {
						return getAdminAuthority();
					}

					// Check if employee user groups' sub groups also contains admingroup
					if (employeeUserGroup.getUserGroups().contains(adminGroup.getUuid())) {
						return getAdminAuthority();
					}
				}
			}

			// Find employee user group and sub user group authority
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

			// User Group
			for (UserGroup userGroup : userGroups) {

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
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
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
	public Set<UserSession> findAllSessions() {
		final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		Set<UserSession> sessions = new LinkedHashSet<UserSession>();

		for (final Object principal : allPrincipals) {
			if (principal instanceof User) {
				final User user = (User) principal;
				if (user.getUuid() != null) {
					List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(user, false);
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
				List<SessionInformation> session = sessionRegistry.getAllSessions(userSession.getUser(), false);
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
			List<SessionInformation> session = sessionRegistry.getAllSessions(userSession.getUser(), false);
			for (SessionInformation sessionInformation : session) {
				sessionInformation.expireNow();
			}
		}
	}

	@Override
	public User getCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			User principal = (User) auth.getPrincipal();
			return principal;
		} else {
			return null;
		}
	}

	@Override
	public User updatePrincipal(User model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User principal = (User) auth.getPrincipal();
		principal.setId(model.getId());
		principal.setName(model.getName());
		principal.setPassword(model.getPassword());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return principal;
	}

	@Override
	public void saveProfilePicture(User model, MultipartFile picture) throws IOException {
		if (picture != null && picture.isEmpty() == Boolean.FALSE) {

			File profilePictureFolder = new File(PROFILE_PICTURE_LOCATION, model.getUuid().toString());
			FileUtils.forceMkdir(profilePictureFolder);

			File original = new File(PROFILE_PICTURE_LOCATION, model.getUuid() + File.separator + "original.png");
			original = new File(original.getAbsolutePath());
			picture.transferTo(original);

			File thumbnail = new File(PROFILE_PICTURE_LOCATION, model.getUuid() + File.separator + "thumbnail.png");
			BufferedImage img = ImageIO.read(original);
			BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, USER_PROFILE_PICTURE_THUMBNAIL_WIDTH, USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT, Scalr.OP_ANTIALIAS);
			ImageIO.write(thumbImg, "png", thumbnail);
		}
	}

	@Override
	public void saveProfilePicture(User customer, InputStream inputStream) throws IOException {

		File profilePictureFolder = new File(PROFILE_PICTURE_LOCATION, customer.getUuid().toString());
		FileUtils.forceMkdir(profilePictureFolder);

		File original = new File(PROFILE_PICTURE_LOCATION, customer.getUuid() + File.separator + "original.png");
		original = new File(original.getAbsolutePath());
		FileUtils.copyInputStreamToFile(inputStream, original);

		File thumbnail = new File(PROFILE_PICTURE_LOCATION, customer.getUuid() + File.separator + "thumbnail.png");
		BufferedImage img = ImageIO.read(original);
		BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, USER_PROFILE_PICTURE_THUMBNAIL_WIDTH, USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT, Scalr.OP_ANTIALIAS);
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
	public Set<UUID> getAllUserGroupsByCurrentUser() throws Exception {
		Set<UUID> userGroupUuids = new HashSet<UUID>();

		User user = getCurrentUser();

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
	public void generateUserField(User model, String configurationDynamicFieldTemplate) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, configurationDynamicFieldTemplate);
		Configuration configuration = modelService.findOneByProperties(properties, Configuration.class);

		if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
			properties = new HashMap<String, Object>();
			properties.put(DynamicFieldTemplate.ID, configuration.getValue());

			DynamicFieldTemplate dynamicFieldTemplate = modelService.findOneByProperties(properties, DynamicFieldTemplate.class);

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
}
