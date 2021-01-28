package com.beanframework.user.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.AdminConstants;
import com.beanframework.user.domain.Admin;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ModelService modelService;

	@Value(AdminConstants.Admin.DEFAULT_ID)
	private String defaultAdminId;

	@Value(AdminConstants.Admin.DEFAULT_PASSWORD)
	private String defaultAdminPassword;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Admin findAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			return null;
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Admin.ID, id);
		Admin entity = modelService.findOneByProperties(properties, Admin.class);

		if (entity == null) {
			if(id.equals(defaultAdminId) && password.equals(defaultAdminPassword)) {
				entity = modelService.create(Admin.class);
				entity.setId(defaultAdminId);
			}
			else {
				throw new BadCredentialsException("Bad Credentials");
			}
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
	public Admin getCurrentUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Admin admin = (Admin) auth.getPrincipal();
			return admin;
		} else {
			return null;
		}
	}

}
