package com.beanframework.core.facade;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@Service
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserService userService;

	@Override
	public UserDto findOneByUuid(UUID uuid) throws Exception {
		User user = userService.findOneEntityByUuid(uuid);
		return modelService.getDto(user, UserDto.class);
	}

	@Override
	public UserDto getCurrentUser() throws Exception {
		User user = userService.getCurrentUser();
		return modelService.getDto(user, UserDto.class);
	}

	@Override
	public UserDto saveProfile(UserDto user) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
