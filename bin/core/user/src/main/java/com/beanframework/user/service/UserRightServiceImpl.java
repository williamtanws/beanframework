package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;

@Service
public class UserRightServiceImpl implements UserRightService {

	@Autowired
	private ModelService modelService;
	
	@Transactional
	@Override
	public void deleteLanguageByLanguageUuid(UUID uuid) {
		List<UserRight> userRights = modelService.findAll();
		for (UserRight userRight : userRights) {
			for (int i = 0; i < userRight.getUserRightFields().size(); i++) {
				if(userRight.getUserRightFields().get(i).getLanguage().getUuid().equals(uuid)) {
					userRight.getUserRightFields().remove(i);
					break;
				}
			}
		}
		modelService.saveEntity(userRights);
	}
}
