package com.beanframework.backoffice.service;

import java.util.UUID;

public interface BackofficeModuleService {

	void deleteAllModuleLanguageByLanguageUuid(UUID uuid);

	void deleteAllModuleUserRightByUserRightUuid(UUID uuid);

	void deleteAllModuleUserPermissionByUserPermissionUuid(UUID uuid);

}
