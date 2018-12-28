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
import com.beanframework.user.domain.UserGroup;

@Component
public class UserGroupFacadeImpl implements UserGroupFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<UserGroup> findPage(Specification<UserGroup> specification, PageRequest pageRequest) throws Exception {
		return modelService.findDtoPage(specification, pageRequest, UserGroup.class);
	}

	@Override
	public UserGroup create() throws Exception {
		return modelService.create(UserGroup.class);
	}
	
	@Override
	public UserGroup findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, UserGroup.class);
	}

	@Override
	public UserGroup findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, UserGroup.class);
	}

	@Override
	public UserGroup createDto(UserGroup model) throws BusinessException {
		return (UserGroup) modelService.saveDto(model, UserGroup.class);
	}

	@Override
	public UserGroup updateDto(UserGroup model) throws BusinessException {
		return (UserGroup) modelService.saveDto(model, UserGroup.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.delete(uuid, UserGroup.class);
	}

	@Override
	public List<UserGroup> findDtoBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findDtoBySorts(sorts, UserGroup.class);
	}

}
