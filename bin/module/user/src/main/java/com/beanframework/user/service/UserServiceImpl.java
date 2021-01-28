package com.beanframework.user.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.springframework.data.jpa.domain.Specification;
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

@Service
public class UserServiceImpl implements UserService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Value(UserConstants.USER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(UserConstants.USER_PROFILE_PICTURE_THUMBNAIL_WIDTH)
	public int USER_PROFILE_PICTURE_THUMBNAIL_WIDTH;

	@Value(UserConstants.USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT)
	public int USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT;

	@Override
	public User findAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Bad Credentials");
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(User.ID, id);

		User entity = modelService.findOneByProperties(properties, User.class);

		if (entity == null) {
			throw new BadCredentialsException("Bad Credentials");
		} else {

			if (passwordEncoder.matches(password, entity.getPassword()) == Boolean.FALSE) {
				throw new BadCredentialsException("Bad Credentials");
			}
		}

		if (entity.getEnabled() == Boolean.FALSE) {
			throw new DisabledException("Account Disabled");
		}

		if (entity.getAccountNonExpired() == Boolean.FALSE) {
			throw new AccountExpiredException("Account Expired");
		}

		if (entity.getAccountNonLocked() == Boolean.FALSE) {
			throw new LockedException("Account Locked");
		}

		if (entity.getCredentialsNonExpired() == Boolean.FALSE) {
			throw new CredentialsExpiredException("Password Expired");
		}

		return entity;
	}

	@Override
	public User getCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			User principal = (User) auth.getPrincipal();
			return modelService.findOneByUuid(principal.getUuid(), User.class);
		} else {
			return null;
		}
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

	@Transactional(readOnly = true)
	@Override
	public Set<GrantedAuthority> getAuthorities(UUID userUuid, String userGroupId) throws Exception {

		User user = modelService.findOneByUuid(userUuid, User.class);
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		for (UUID uuid : user.getUserGroups()) {
			UserGroup entity = modelService.findOneByUuid(uuid, UserGroup.class);
			Hibernate.initialize(entity.getUserAuthorities());
			if(entity != null) {
				userGroups.add(entity);
			}
		}

		Set<UUID> checkedUserGroupUuid = new HashSet<UUID>();
		for (UserGroup userGroup : userGroups) {
			checkedUserGroupUuid.add(userGroup.getUuid());
		}

		for (UserGroup userGroup : userGroups) {
			initializeUserGroupUuids(userGroup, checkedUserGroupUuid);
		}
		boolean isAuthorized = false;

		for (UserGroup userGroup : userGroups) {
			if (isAuthorized == Boolean.FALSE) {
				isAuthorized = isAuthorized(userGroup, userGroupId);
			} else {
				break;
			}
		}

		if (isAuthorized) {
			return getAuthorities(userGroups, new HashSet<String>());
		} else {
			return new HashSet<GrantedAuthority>();
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserGroup> getUserGroupsByCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			User principal = (User) auth.getPrincipal();

			User user = modelService.findOneByUuid(principal.getUuid(), User.class);
			List<UserGroup> userGroups = new ArrayList<UserGroup>();
			for (UUID uuid : user.getUserGroups()) {
				UserGroup entity = modelService.findOneByUuid(uuid, UserGroup.class);
				Hibernate.initialize(entity.getUserAuthorities());
				if(entity != null) {
					userGroups.add(entity);
				}
			}

			Set<UUID> checkedUserGroupUuid = new HashSet<UUID>();
			for (UserGroup userGroup : userGroups) {
				checkedUserGroupUuid.add(userGroup.getUuid());
			}

			for (UserGroup userGroup : userGroups) {
				initializeUserGroupUuids(userGroup, checkedUserGroupUuid);
			}

			return userGroups;

		} else {
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<UUID> getAllUserGroupUuidsByCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User principal = (User) auth.getPrincipal();
		
		return getAllUserGroupUuidsByUserUuid(principal.getUuid());
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<UUID> getAllUserGroupUuidsByUserUuid(UUID userUuid) throws Exception {
	
		User user = modelService.findOneByUuid(userUuid, User.class);
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		for (UUID uuid : user.getUserGroups()) {
			UserGroup entity = modelService.findOneByUuid(uuid, UserGroup.class);
			Hibernate.initialize(entity.getUserAuthorities());
			if(entity != null) {
				userGroups.add(entity);
			}
		}

		Set<UUID> checkedUserGroupUuid = new HashSet<UUID>();
		for (UserGroup userGroup : userGroups) {
			checkedUserGroupUuid.add(userGroup.getUuid());
		}

		for (UserGroup userGroup : userGroups) {
			initializeUserGroupUuids(userGroup, checkedUserGroupUuid);
		}

		return checkedUserGroupUuid;
	}

	@Transactional(readOnly = true)
	private void initializeUserGroupUuids(UserGroup userGroup, Set<UUID> checkedUserGroupUuids) {

		Hibernate.initialize(userGroup.getUserGroups());

		for (UserGroup child : userGroup.getUserGroups()) {
			if (checkedUserGroupUuids.contains(child.getUuid()) == Boolean.FALSE) {
				checkedUserGroupUuids.add(child.getUuid());

				if (child.getUserGroups() != null && child.getUserGroups().isEmpty() == Boolean.FALSE) {
					initializeUserGroupUuids(child, checkedUserGroupUuids);
				}

			}
		}
	}
	

	
	@Transactional(readOnly = true)
	@Override
	public Set<String> getAllUserGroupIdsByUserUuid(UUID userUuid) throws Exception {
	
		User user = modelService.findOneByUuid(userUuid, User.class);
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		for (UUID uuid : user.getUserGroups()) {
			UserGroup entity = modelService.findOneByUuid(uuid, UserGroup.class);
			Hibernate.initialize(entity.getUserAuthorities());
			if(entity != null) {
				userGroups.add(entity);
			}
		}
		

		Set<String> checkedUserGroupId = new HashSet<String>();
		for (UserGroup userGroup : userGroups) {
			checkedUserGroupId.add(userGroup.getId());
		}

		for (UserGroup userGroup : userGroups) {
			initializeUserGroupIds(userGroup, checkedUserGroupId);
		}

		return checkedUserGroupId;
	}
	
	@Transactional(readOnly = true)
	private void initializeUserGroupIds(UserGroup userGroup, Set<String> checkedUserGroupIds) {

		Hibernate.initialize(userGroup.getUserGroups());

		for (UserGroup child : userGroup.getUserGroups()) {
			if (checkedUserGroupIds.contains(child.getUuid().toString()) == Boolean.FALSE) {
				checkedUserGroupIds.add(child.getId());

				if (child.getUserGroups() != null && child.getUserGroups().isEmpty() == Boolean.FALSE) {
					initializeUserGroupIds(child, checkedUserGroupIds);
				}

			}
		}
	}

	private boolean isAuthorized(UserGroup userGroup, String userGroupId) {
		
		if(userGroup.getId().equals(userGroupId)) {
			return true;
		}

		for (UserGroup child : userGroup.getUserGroups()) {
			if (child.getId().equals(userGroupId)) {
				return true;
			} else {
				return isAuthorized(child, userGroupId);
			}
		}

		return false;
	}

	private Set<GrantedAuthority> getAuthorities(List<UserGroup> userGroups, Set<String> processedUserGroupUuids) {

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		for (UserGroup userGroup : userGroups) {
			if (processedUserGroupUuids.contains(userGroup.getUuid().toString()) == Boolean.FALSE) {
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

				if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == Boolean.FALSE) {
					authorities.addAll(getAuthorities(userGroup.getUserGroups(), processedUserGroupUuids));
				}
			}
		}

		return authorities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsersByUserGroupUuid(UUID uuid) throws Exception {
		Query query = entityManager.createQuery(UserConstants.Query.SELECT_USER_BY_USER_GROUP);
		query.setParameter("uuid", uuid);
		
		return query.getResultList();
	}

	@Override
	public void removeUserGroupsRel(UserGroup model) throws Exception {
		Specification<User> specification = new Specification<User>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(User.USER_GROUPS, JoinType.LEFT).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<User> entities = modelService.findBySpecificationBySort(specification, User.class);

		for (int i = 0; i < entities.size(); i++) {

			boolean removed = false;
			for (int j = 0; j < entities.get(i).getUserGroups().size(); j++) {
				entities.get(i).getUserGroups().remove(model.getUuid());
			}

			if (removed)
				modelService.saveEntityQuietly(entities.get(i), User.class);
		}
	}
}
