package com.beanframework.core.facade;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;
import com.beanframework.user.specification.UserSpecification;

@Service
public class UserFacadeImpl extends AbstractFacade<User, UserDto> implements UserFacade {

	private static final Class<User> entityClass = User.class;
	private static final Class<UserDto> dtoClass = UserDto.class;

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserService userService;

	@Override
	public UserDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);

	}

	@Override
	public UserDto getCurrentUser() throws Exception {
		User user = userService.getCurrentUser();
		return modelService.getDto(user, dtoClass);
	}

	@Override
	public UserDto saveProfile(UserDto dto) throws BusinessException {
		try {
			User entity = modelService.getEntity(dto, entityClass);
			entity = (User) modelService.saveEntity(entity);

			return modelService.getDto(entity, dtoClass);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<UserDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, UserSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);

	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

}
