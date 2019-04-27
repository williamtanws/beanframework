package com.beanframework.user.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRightField;

@Service
public class UserServiceImpl implements UserService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value(UserConstants.USER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(UserConstants.USER_PROFILE_PICTURE_THUMBNAIL_WIDTH)
	public int USER_PROFILE_PICTURE_THUMBNAIL_WIDTH;

	@Value(UserConstants.USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT)
	public int USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT;

	@Override
	public User findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, User.class);
	}

	@Override
	public User findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, User.class);
	}

	@Transactional(readOnly = true)
	@Override
	public User findAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Bad Credentials");
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(User.ID, id);

		User entity = findOneEntityByProperties(properties);

		if (entity == null) {
			throw new BadCredentialsException("Bad Credentials");
		} else {

			if (passwordEncoder.matches(password, entity.getPassword()) == false) {
				throw new BadCredentialsException("Bad Credentials");
			}
		}

		if (entity.getEnabled() == false) {
			throw new DisabledException("Account Disabled");
		}

		if (entity.getAccountNonExpired() == false) {
			throw new AccountExpiredException("Account Expired");
		}

		if (entity.getAccountNonLocked() == false) {
			throw new LockedException("Account Locked");
		}

		if (entity.getCredentialsNonExpired() == false) {
			throw new CredentialsExpiredException("Password Expired");
		}

		return entity;
	}

	@Override
	public User getCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			User principal = (User) auth.getPrincipal();
			return findOneEntityByUuid(principal.getUuid());
		} else {
			return null;
		}
	}

	@Override
	public void saveProfilePicture(User model, MultipartFile picture) throws IOException {
		if (picture != null && picture.isEmpty() == false) {

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
	public void deleteProfilePictureByUuid(UUID uuid) {
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
	public Set<GrantedAuthority> getAuthorities(UUID userUuid, String userGroupId) throws Exception {

		User user = modelService.findOneEntityByUuid(userUuid, User.class);
		Hibernate.initialize(user.getUserGroups());

		Set<String> checkedUserGroupUuid = new HashSet<String>();
		for (UserGroup userGroup : user.getUserGroups()) {
			checkedUserGroupUuid.add(userGroup.getUuid().toString());
		}

		boolean isAuthorized = false;

		for (UserGroup userGroup : user.getUserGroups()) {
			if (userGroup.getId().equals(userGroupId)) {
				isAuthorized = true;
			} else {
				checkedUserGroupUuid.add(userGroup.getUuid().toString());
				if (isAuthorized(userGroup, userGroupId, checkedUserGroupUuid))
					isAuthorized = true;
			}
		}

		if (isAuthorized) {
			return getAuthorities(user.getUserGroups(), new HashSet<String>());
		} else {
			return new HashSet<GrantedAuthority>();
		}
	}

	@Transactional
	@Override
	public List<UserGroup> getUserGroupsByCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			User principal = (User) auth.getPrincipal();

			User user = modelService.findOneEntityByUuid(principal.getUuid(), User.class);

			Hibernate.initialize(user.getUserGroups());

			Set<String> checkedUserGroupUuid = new HashSet<String>();
			for (UserGroup userGroup : user.getUserGroups()) {
				checkedUserGroupUuid.add(userGroup.getUuid().toString());
				
				Hibernate.initialize(userGroup.getUserAuthorities());

				for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {
					Hibernate.initialize(userAuthority.getUserRight());
					for (UserRightField field : userAuthority.getUserRight().getFields()) {
						Hibernate.initialize(field.getDynamicFieldSlot());
						if (field.getDynamicFieldSlot() != null)
							Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
						if (field.getDynamicFieldSlot().getDynamicField() != null)
							Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
					}
					Hibernate.initialize(userAuthority.getUserPermission());
					for (UserPermissionField field : userAuthority.getUserPermission().getFields()) {
						Hibernate.initialize(field.getDynamicFieldSlot());
						if (field.getDynamicFieldSlot() != null)
							Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
						if (field.getDynamicFieldSlot().getDynamicField() != null)
							Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
					}
				}
			}
			
			for (UserGroup userGroup : user.getUserGroups()) {
				initializeUserGroups(userGroup, checkedUserGroupUuid);
			}
			
			return user.getUserGroups();

		} else {
			return null;
		}
	}

	private void initializeUserGroups(UserGroup userGroup, Set<String> checkedUserGroupUuid) {
		if (checkedUserGroupUuid.contains(userGroup.getUuid().toString()) == false) {
			
			Hibernate.initialize(userGroup.getUserGroups());
			
			for (UserGroup child : userGroup.getUserGroups()) {
				checkedUserGroupUuid.add(child.getUuid().toString());
				
				Hibernate.initialize(userGroup.getUserAuthorities());

				for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {
					Hibernate.initialize(userAuthority.getUserRight());
					for (UserRightField field : userAuthority.getUserRight().getFields()) {
						Hibernate.initialize(field.getDynamicFieldSlot());
						if (field.getDynamicFieldSlot() != null)
							Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
						if (field.getDynamicFieldSlot().getDynamicField() != null)
							Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
					}
					Hibernate.initialize(userAuthority.getUserPermission());
					for (UserPermissionField field : userAuthority.getUserPermission().getFields()) {
						Hibernate.initialize(field.getDynamicFieldSlot());
						if (field.getDynamicFieldSlot() != null)
							Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField());
						if (field.getDynamicFieldSlot().getDynamicField() != null)
							Hibernate.initialize(field.getDynamicFieldSlot().getDynamicField().getEnumerations());
					}
				}
			}
			
			for (UserGroup child : userGroup.getUserGroups()) {
				initializeUserGroups(child, checkedUserGroupUuid);
			}
		}
	}

	private boolean isAuthorized(UserGroup userGroup, String userGroupId, Set<String> checkedUserGroupUuid) {

		if (checkedUserGroupUuid.contains(userGroup.getUuid().toString())) {
			return false;
		}

		Hibernate.initialize(userGroup.getUserGroups());

		for (UserGroup child : userGroup.getUserGroups()) {
			checkedUserGroupUuid.add(child.getUuid().toString());
		}

		for (UserGroup child : userGroup.getUserGroups()) {
			if (child.getId().equals(userGroupId)) {
				return true;
			} else {
				return isAuthorized(child, userGroupId, checkedUserGroupUuid);
			}
		}

		return false;
	}

	private Set<GrantedAuthority> getAuthorities(List<UserGroup> userGroups, Set<String> processedUserGroupUuids) {

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		for (UserGroup userGroup : userGroups) {
			if (processedUserGroupUuids.contains(userGroup.getUuid().toString()) == false) {
				processedUserGroupUuids.add(userGroup.getUuid().toString());

				for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {

					if (Boolean.TRUE.equals(userAuthority.getEnabled())) {
						StringBuilder authority = new StringBuilder();

						authority.append(userAuthority.getUserPermission().getId());
						authority.append("_");
						authority.append(userAuthority.getUserRight().getId());

						authorities.add(new SimpleGrantedAuthority(authority.toString()));
					}

				}

				if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false) {
					authorities.addAll(getAuthorities(userGroup.getUserGroups(), processedUserGroupUuids));
				}
			}
		}

		return authorities;
	}
}
