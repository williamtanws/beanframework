package com.beanframework.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.service.ModelService;

@Component
public class AdminFacadeImpl implements AdminFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AdminService adminService;

	@Override
	public Admin getCurrentAdmin() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Admin admin = (Admin) auth.getPrincipal();
			return admin;
		} else {
			return null;
		}
	}

	@Override
	public Admin authenticate(String id, String password) {
		Admin admin = adminService.authenticate(id, password);

		if (admin == null) {
			throw new BadCredentialsException("Bad Credentials");
		}
		if (admin.isEnabled() == false) {
			throw new DisabledException("Account Disabled");
		}

		if (admin.isAccountNonExpired() == false) {
			throw new AccountExpiredException("Account Expired");
		}

		if (admin.isAccountNonLocked() == false) {
			throw new LockedException("Account Locked");
		}

		if (admin.isCredentialsNonExpired() == false) {
			throw new CredentialsExpiredException("Password Expired");
		}
		return modelService.getDto(admin);
	}
}
