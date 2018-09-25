package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.validator.DeleteUserPermissionValidator;
import com.beanframework.user.validator.SaveUserPermissionValidator;

@Component
public class UserPermissionFacadeImpl implements UserPermissionFacade {

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private SaveUserPermissionValidator saveUserPermissionValidator;
	
	@Autowired
	private DeleteUserPermissionValidator deleteUserPermissionValidator;

	@Override
	public UserPermission create() {
		return userPermissionService.create();
	}

	@Override
	public UserPermission initDefaults(UserPermission userPermission) {
		return userPermissionService.initDefaults(userPermission);
	}

	@Override
	public UserPermission save(UserPermission userPermission, Errors bindingResult) {
		saveUserPermissionValidator.validate(userPermission, bindingResult);

		if (bindingResult.hasErrors()) {
			return userPermission;
		}

		return userPermissionService.save(userPermission);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteUserPermissionValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			userPermissionService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		userPermissionService.deleteAll();
	}

	@Override
	public UserPermission findByUuid(UUID uuid) {
		return userPermissionService.findByUuid(uuid);
	}

	@Override
	public UserPermission findById(String id) {
		return userPermissionService.findById(id);
	}
	
	@Override
	public List<UserPermission> findByOrderByCreatedDate() {
		return userPermissionService.findByOrderByCreatedDateDesc();
	}

	@Override
	public Page<UserPermission> page(UserPermission userPermission, int page, int size, Direction direction,
			String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return userPermissionService.page(userPermission, pageRequest);
	}

	public UserPermissionService getUserPermissionService() {
		return userPermissionService;
	}

	public void setUserPermissionService(UserPermissionService userPermissionService) {
		this.userPermissionService = userPermissionService;
	}

	public SaveUserPermissionValidator getSaveUserPermissionValidator() {
		return saveUserPermissionValidator;
	}

	public void setSaveUserPermissionValidator(SaveUserPermissionValidator saveUserPermissionValidator) {
		this.saveUserPermissionValidator = saveUserPermissionValidator;
	}

}
