package com.beanframework.user.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.beanframework.common.context.FetchContext;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private FetchContext fetchContext;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties();

		fetchContext.addFetchProperty(User.class, User.USER_GROUPS);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_AUTHORITIES);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_GROUPS);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_PERMISSION);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_RIGHT);

		fetchContext.addFetchProperty(User.class, User.FIELDS);
		fetchContext.addFetchProperty(UserField.class, UserField.DYNAMIC_FIELD_SLOT);
		fetchContext.addFetchProperty(DynamicFieldSlot.class, DynamicFieldSlot.DYNAMIC_FIELD);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.LANGUAGE);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.ENUMERATIONS);

		return modelService.findOneEntityByUuid(uuid, User.class);
	}

	@Override
	public User findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties();

		fetchContext.addFetchProperty(User.class, User.USER_GROUPS);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_AUTHORITIES);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_GROUPS);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_PERMISSION);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_RIGHT);

		fetchContext.addFetchProperty(User.class, User.FIELDS);
		fetchContext.addFetchProperty(UserField.class, UserField.DYNAMIC_FIELD_SLOT);
		fetchContext.addFetchProperty(DynamicFieldSlot.class, DynamicFieldSlot.DYNAMIC_FIELD);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.LANGUAGE);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.ENUMERATIONS);

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
	public Set<GrantedAuthority> getAuthorities(List<UserGroup> userGroups, Set<String> processedUserGroupUuids) {

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
}
