package com.beanframework.core.facade;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.populator.EmployeeFullPopulator;
import com.beanframework.core.converter.populator.UserFullPopulator;
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

	@Autowired
	private UserFullPopulator userFullPopulator;

	@Autowired
	private EmployeeFullPopulator employeeFullPopulator;

	@Override
	public UserDto findOneByUuid(UUID uuid) throws Exception {
		User user = modelService.findOneByUuid(uuid, User.class);
		return modelService.getDto(user, UserDto.class, new DtoConverterContext(userFullPopulator));
	}

	@Override
	public UserDto getCurrentUser() throws Exception {
		User user = userService.getCurrentUser();
		return modelService.getDto(user, UserDto.class, new DtoConverterContext(userFullPopulator));
	}

	@Override
	public UserDto saveProfile(UserDto dto) throws BusinessException {
		try {
			User entity = modelService.getEntity(dto, User.class);
			entity = (User) modelService.saveEntity(entity, User.class);
			
			return modelService.getDto(entity, EmployeeDto.class, new DtoConverterContext(employeeFullPopulator));

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
