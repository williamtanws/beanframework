package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserPermission;

@Component
public class UserPermissionFacadeImpl implements UserPermissionFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<UserPermission> findPage(Specification<UserPermission> specification, PageRequest pageRequest) throws Exception {
		return modelService.findDtoPage(specification, pageRequest, UserPermission.class);
	}

	@Override
	public UserPermission create() throws Exception {
		return modelService.create(UserPermission.class);
	}

	@Override
	public UserPermission findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, UserPermission.class);
	}

	@Override
	public void createDto(UserPermission model) throws BusinessException {
		modelService.saveDto(model, UserPermission.class);
	}

	@Override
	public void updateDto(UserPermission model) throws BusinessException {
		modelService.saveDto(model, UserPermission.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.delete(uuid, UserPermission.class);
	}

	@Override
	public List<UserPermission> findDtoBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findDtoBySorts(sorts, UserPermission.class);
	}
}
