package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.user.domain.UserRight;
import com.beanframework.user.validator.DeleteUserRightValidator;
import com.beanframework.user.validator.SaveUserRightValidator;

@Component
public class UserRightFacadeImpl implements UserRightFacade {

	@Autowired
	private UserRightService userRightService;

	@Autowired
	private SaveUserRightValidator saveUserRightValidator;
	
	@Autowired
	private DeleteUserRightValidator deleteUserRightValidator;

	@Override
	public UserRight create() {
		return userRightService.create();
	}

	@Override
	public UserRight initDefaults(UserRight userRight) {
		return userRightService.initDefaults(userRight);
	}

	@Override
	public UserRight save(UserRight userRight, Errors bindingResult) {
		saveUserRightValidator.validate(userRight, bindingResult);

		if (bindingResult.hasErrors()) {
			return userRight;
		}

		return userRightService.save(userRight);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteUserRightValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			userRightService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		userRightService.deleteAll();
	}

	@Override
	public UserRight findByUuid(UUID uuid) {
		return userRightService.findByUuid(uuid);
	}

	@Override
	public UserRight findById(String id) {
		return userRightService.findById(id);
	}
	
	@Override
	public List<UserRight> findByOrderByCreatedDate() {
		return userRightService.findByOrderByCreatedDateDesc();
	}


	@Override
	public Page<UserRight> page(UserRight userRight, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return userRightService.page(userRight, pageRequest);
	}

	public UserRightService getUserRightService() {
		return userRightService;
	}

	public void setUserRightService(UserRightService userRightService) {
		this.userRightService = userRightService;
	}

	public SaveUserRightValidator getSaveUserRightValidator() {
		return saveUserRightValidator;
	}

	public void setSaveUserRightValidator(SaveUserRightValidator saveUserRightValidator) {
		this.saveUserRightValidator = saveUserRightValidator;
	}
}
