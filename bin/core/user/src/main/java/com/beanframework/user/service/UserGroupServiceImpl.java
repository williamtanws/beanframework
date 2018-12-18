package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupSpecification;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private ModelService modelService;
	

	@Transactional
	@Override
	public void deleteLanguageByLanguageUuid(UUID uuid) {
		List<UserGroup> userGroups = modelService.findAll(UserGroup.class);
		for (UserGroup userGroup : userGroups) {
			for (int i = 0; i < userGroup.getUserGroupFields().size(); i++) {
				if(userGroup.getUserGroupFields().get(i).getLanguage().getUuid().equals(uuid)) {
					userGroup.getUserGroupFields().remove(i);
					break;
				}
			}
		}
		modelService.saveEntity(userGroups);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<UserGroup> page(UserGroup userGroup, Pageable pageable) {
		Page<UserGroup> page = modelService.findPage(UserGroupSpecification.findByCriteria(userGroup), pageable, UserGroup.class);
		return page;
	}
}
