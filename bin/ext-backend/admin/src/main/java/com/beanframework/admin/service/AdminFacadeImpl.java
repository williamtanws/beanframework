package com.beanframework.admin.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.validator.DeleteAdminValidator;
import com.beanframework.admin.validator.SaveAdminValidator;

@Component
public class AdminFacadeImpl implements AdminFacade {

	@Autowired
	private AdminService adminService;

	@Autowired
	private SaveAdminValidator saveAdminValidator;

	@Autowired
	private DeleteAdminValidator deleteAdminValidator;

	@Override
	public Admin create() {
		return adminService.create();
	}

	@Override
	public Admin initDefaults(Admin admin) {
		return adminService.initDefaults(admin);
	}

	@Override
	public Admin save(Admin admin, Errors bindingResult) {
		saveAdminValidator.validate(admin, bindingResult);

		if (bindingResult.hasErrors()) {
			return admin;
		}

		return adminService.save(admin);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteAdminValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			adminService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		adminService.deleteAll();
	}

	@Override
	public Admin findByUuid(UUID uuid) {
		return adminService.findByUuid(uuid);
	}

	@Override
	public Admin findById(String id) {
		return adminService.findById(id);
	}

	@Override
	public Page<Admin> page(Admin admin, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return adminService.page(admin, pageRequest);
	}

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
			throw new CredentialsExpiredException("Passwrd Expired");
		}
		return admin;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public SaveAdminValidator getSaveAdminValidator() {
		return saveAdminValidator;
	}

	public void setSaveAdminValidator(SaveAdminValidator saveAdminValidator) {
		this.saveAdminValidator = saveAdminValidator;
	}

	public DeleteAdminValidator getDeleteAdminValidator() {
		return deleteAdminValidator;
	}

	public void setDeleteAdminValidator(DeleteAdminValidator deleteAdminValidator) {
		this.deleteAdminValidator = deleteAdminValidator;
	}

}
