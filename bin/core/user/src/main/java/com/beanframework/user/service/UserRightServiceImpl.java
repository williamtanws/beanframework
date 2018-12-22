package com.beanframework.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;

@Service
public class UserRightServiceImpl implements UserRightService {

	@Autowired
	private ModelService modelService;
	
	@Transactional
	@Override
	public void deleteLanguageByLanguageUuid(UUID uuid) throws Exception {
//		List<UserRight> userRights = modelService.findAll(UserRight.class);
//		for (UserRight userRight : userRights) {
//			for (int i = 0; i < userRight.getUserRightFields().size(); i++) {
//				if(userRight.getUserRightFields().get(i).getLanguage().getUuid().equals(uuid)) {
//					userRight.getUserRightFields().remove(i);
//					break;
//				}
//			}
//		}
//		modelService.saveEntity(userRights);
	}
}
