package com.beanframework.core.facade;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.EmployeeDto;
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
		User user = modelService.findByUuid(uuid, User.class);
		return modelService.getDto(user, UserDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public UserDto getCurrentUser() throws Exception {
		User user = userService.getCurrentUser();
		return modelService.getDto(user, UserDto.class);
	}

	@Override
	public UserDto saveProfile(UserDto dto) throws BusinessException {
		try {
			User entity = modelService.getEntity(dto, User.class);
			entity = (User) modelService.saveEntity(entity, User.class);
			
			return modelService.getDto(entity, EmployeeDto.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
