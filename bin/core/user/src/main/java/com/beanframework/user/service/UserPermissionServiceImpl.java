package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserPermission;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {

	@Autowired
	private ModelService modelService;

	@Transactional
	@Override
	public void deleteLanguageByLanguageUuid(UUID uuid) {
		List<UserPermission> userPermissions = modelService.findAll();
		for (UserPermission userPermission : userPermissions) {
			Hibernate.initialize(userPermission.getUserPermissionLangs());
			for (int i = 0; i < userPermission.getUserPermissionLangs().size(); i++) {
				Hibernate.initialize(userPermission.getUserPermissionLangs().get(i).getLanguage());
				if(userPermission.getUserPermissionLangs().get(i).getLanguage().getUuid().equals(uuid)) {
					userPermission.getUserPermissionLangs().remove(i);
					break;
				}
			}
		}
		modelService.save(userPermissions);
	}
}
