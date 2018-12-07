package com.beanframework.backoffice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.backoffice.validator.DeleteBackofficeModuleLanguageValidator;
import com.beanframework.backoffice.validator.DeleteBackofficeModuleUserPermissionValidator;
import com.beanframework.backoffice.validator.DeleteBackofficeModuleUserRightValidator;

@Component
public class BackofficeModuleFacadeImpl implements BackofficeModuleFacade {

	@Autowired
	private BackofficeModuleService backofficeModuleService;

	@Autowired
	private DeleteBackofficeModuleLanguageValidator deleteBackofficeModuleLanguageValidator;

	@Autowired
	private DeleteBackofficeModuleUserRightValidator deleteBackofficeModuleUserRightValidator;

	@Autowired
	private DeleteBackofficeModuleUserPermissionValidator deleteBackofficeModuleUserPermissionValidator;

	@Override
	public void deleteAllModuleLanguageByLanguageUuid(UUID uuid, Errors bindingResult) {
		deleteBackofficeModuleLanguageValidator.validate(uuid, bindingResult);

		if (bindingResult.hasErrors() == false) {
			backofficeModuleService.deleteAllModuleLanguageByLanguageUuid(uuid);
		}
	}

	@Override
	public void deleteAllModuleUserRightByUserRightUuid(UUID uuid, Errors bindingResult) {
		deleteBackofficeModuleUserRightValidator.validate(uuid, bindingResult);

		if (bindingResult.hasErrors() == false) {
			backofficeModuleService.deleteAllModuleUserRightByUserRightUuid(uuid);
		}
	}

	@Override
	public void deleteAllModuleUserPermissionByUserPermissionUuid(UUID uuid, Errors bindingResult) {
		deleteBackofficeModuleUserPermissionValidator.validate(uuid, bindingResult);

		if (bindingResult.hasErrors() == false) {
			backofficeModuleService.deleteAllModuleUserPermissionByUserPermissionUuid(uuid);
		}
	}

	public BackofficeModuleService getBackofficeLanguageService() {
		return backofficeModuleService;
	}

	public void setBackofficeLanguageService(BackofficeModuleService backofficeModuleService) {
		this.backofficeModuleService = backofficeModuleService;
	}

	public BackofficeModuleService getBackofficeModuleService() {
		return backofficeModuleService;
	}

	public void setBackofficeModuleService(BackofficeModuleService backofficeModuleService) {
		this.backofficeModuleService = backofficeModuleService;
	}

	public DeleteBackofficeModuleLanguageValidator getDeleteBackofficeModuleLanguageValidator() {
		return deleteBackofficeModuleLanguageValidator;
	}

	public void setDeleteBackofficeModuleLanguageValidator(
			DeleteBackofficeModuleLanguageValidator deleteBackofficeModuleLanguageValidator) {
		this.deleteBackofficeModuleLanguageValidator = deleteBackofficeModuleLanguageValidator;
	}

	public DeleteBackofficeModuleUserRightValidator getDeleteBackofficeModuleUserRightValidator() {
		return deleteBackofficeModuleUserRightValidator;
	}

	public void setDeleteBackofficeModuleUserRightValidator(
			DeleteBackofficeModuleUserRightValidator deleteBackofficeModuleUserRightValidator) {
		this.deleteBackofficeModuleUserRightValidator = deleteBackofficeModuleUserRightValidator;
	}

	public DeleteBackofficeModuleUserPermissionValidator getDeleteBackofficeModuleUserPermissionValidator() {
		return deleteBackofficeModuleUserPermissionValidator;
	}

	public void setDeleteBackofficeModuleUserPermissionValidator(
			DeleteBackofficeModuleUserPermissionValidator deleteBackofficeModuleUserPermissionValidator) {
		this.deleteBackofficeModuleUserPermissionValidator = deleteBackofficeModuleUserPermissionValidator;
	}

}
