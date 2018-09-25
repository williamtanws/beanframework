package com.beanframework.backoffice.service;

import java.util.UUID;

import org.springframework.validation.Errors;

public interface BackofficeModuleFacade {

	void deleteAllModuleLanguageByLanguageUuid(UUID uuid, Errors bindingResult);
	
	void deleteAllModuleUserRightByUserRightUuid(UUID uuid, Errors bindingResult);
	
	void deleteAllModuleUserPermissionByUserPermissionUuid(UUID uuid, Errors bindingResult);

}
