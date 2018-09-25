package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.validator.DeleteUserGroupValidator;
import com.beanframework.user.validator.SaveUserGroupValidator;

@Component
public class UserGroupFacadeImpl implements UserGroupFacade {

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private SaveUserGroupValidator saveUserGroupValidator;
	
	@Autowired
	private DeleteUserGroupValidator deleteUserGroupValidator;

	@Override
	public UserGroup create() {
		return userGroupService.create();
	}

	@Override
	public UserGroup initDefaults(UserGroup userGroup) {
		return userGroupService.initDefaults(userGroup);
	}

	@Override
	public UserGroup save(UserGroup userGroup, Errors bindingResult) {
		saveUserGroupValidator.validate(userGroup, bindingResult);

		if (bindingResult.hasErrors()) {
			return userGroup;
		}

		return userGroupService.save(userGroup);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteUserGroupValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			userGroupService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		userGroupService.deleteAll();
	}

	@Override
	public UserGroup findByUuid(UUID uuid) {
		return userGroupService.findByUuid(uuid);
	}

	@Override
	public UserGroup findById(String id) {
		return userGroupService.findById(id);
	}
	
	@Override
	public List<UserGroup> findByOrderByCreatedDate() {
		return userGroupService.findByOrderByCreatedDate();
	}

	@Override
	public Page<UserGroup> page(UserGroup userGroup, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return userGroupService.page(userGroup, pageRequest);
	}

	public UserGroupService getUserGroupService() {
		return userGroupService;
	}

	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	public SaveUserGroupValidator getSaveUserGroupValidator() {
		return saveUserGroupValidator;
	}

	public void setSaveUserGroupValidator(SaveUserGroupValidator saveUserGroupValidator) {
		this.saveUserGroupValidator = saveUserGroupValidator;
	}
}
