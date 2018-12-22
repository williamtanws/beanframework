package com.beanframework.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private ModelService modelService;
	

	@Transactional
	@Override
	public void deleteLanguageByLanguageUuid(UUID uuid) throws Exception {
//		List<UserGroup> userGroups = modelService.findAll(UserGroup.class);
//		for (UserGroup userGroup : userGroups) {
//			for (int i = 0; i < userGroup.getUserGroupFields().size(); i++) {
//				if(userGroup.getUserGroupFields().get(i).getLanguage().getUuid().equals(uuid)) {
//					userGroup.getUserGroupFields().remove(i);
//					break;
//				}
//			}
//		}
//		modelService.saveEntity(userGroups);
	}
}
