package com.beanframework.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupSpecification;

@Component
public class UserGroupFacadeImpl implements UserGroupFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<UserGroup> page(UserGroup userGroup, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		Page<UserGroup> userGroupPage = modelService.findPage(UserGroupSpecification.findByCriteria(userGroup), pageRequest, UserGroup.class);
		
		List<UserGroup> content = modelService.getDto(userGroupPage.getContent());
		return new PageImpl<UserGroup>(content, userGroupPage.getPageable(), userGroupPage.getTotalElements());
	}
}
