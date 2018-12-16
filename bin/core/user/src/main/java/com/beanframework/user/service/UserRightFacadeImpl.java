package com.beanframework.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightSpecification;

@Component
public class UserRightFacadeImpl implements UserRightFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<UserRight> page(UserRight userRight, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		Page<UserRight> userRightPage = modelService.findPage(UserRightSpecification.findByCriteria(userRight), pageRequest, UserRight.class);
		
		List<UserRight> content = modelService.getDto(userRightPage.getContent());
		return new PageImpl<UserRight>(content, userRightPage.getPageable(), userRightPage.getTotalElements());
	}
}
