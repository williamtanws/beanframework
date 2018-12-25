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
import com.beanframework.user.domain.UserRight;

@Component
public class UserRightFacadeImpl implements UserRightFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private UserRightService userRightService;

	@Override
	public Page<UserRight> findPage(Specification<UserRight> specification, PageRequest pageRequest) throws Exception {
		return modelService.findPage(specification, pageRequest, UserRight.class);
	}

	@Override
	public UserRight create() throws Exception {
		return modelService.create(UserRight.class);
	}

	@Override
	public UserRight findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, UserRight.class);
	}

	@Override
	public void createDto(UserRight model) throws BusinessException {
		modelService.saveDto(model, UserRight.class);
	}

	@Override
	public void updateDto(UserRight model) throws BusinessException {
		modelService.saveDto(model, UserRight.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		userRightService.delete(uuid);
	}

	@Override
	public List<UserRight> findDtoBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findDtoBySorts(sorts, UserRight.class);
	}
}
