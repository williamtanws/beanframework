package com.beanframework.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionSpecification;

@Component
public class UserPermissionFacadeImpl implements UserPermissionFacade {
	
	@Autowired
	private ModelService modelService;

	@Override
	public Page<UserPermission> page(UserPermission userPermission, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		Page<UserPermission> userPermissionPage = modelService.findPage(UserPermissionSpecification.findByCriteria(userPermission), pageRequest, UserPermission.class);
		
		List<UserPermission> content = modelService.getDto(userPermissionPage.getContent());
		return new PageImpl<UserPermission>(content, userPermissionPage.getPageable(), userPermissionPage.getTotalElements());
	}

}
