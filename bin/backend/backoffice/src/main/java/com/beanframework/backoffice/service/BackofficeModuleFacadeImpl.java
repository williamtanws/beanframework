//package com.beanframework.backoffice.service;
//
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//
//import com.beanframework.common.exception.BusinessException;
//
//@Component
//public class BackofficeModuleFacadeImpl implements BackofficeModuleFacade {
//
//	@Autowired
//	private BackofficeModuleService backofficeModuleService;
//
//	@Override
//	public void deleteAllModuleLanguageByLanguageUuid(UUID uuid, Errors bindingResult) {
//		backofficeModuleService.deleteAllModuleLanguageByLanguageUuid(uuid);
//	}
//
//	@Override
//	public void deleteAllModuleUserRightByUserRightUuid(UUID uuid, Errors bindingResult) throws BusinessException {
//		backofficeModuleService.deleteAllModuleUserRightByUserRightUuid(uuid);
//	}
//
//	@Override
//	public void deleteAllModuleUserPermissionByUserPermissionUuid(UUID uuid, Errors bindingResult) throws BusinessException {
//		backofficeModuleService.deleteAllModuleUserPermissionByUserPermissionUuid(uuid);
//	}
//
//}
