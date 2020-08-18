package com.beanframework.core.facade;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.populator.UserBasicPopulator;
import com.beanframework.core.converter.populator.UserFullPopulator;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;
import com.beanframework.user.specification.UserSpecification;

@Service
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserFullPopulator userFullPopulator;

	@Autowired
	private UserFullPopulator employeeFullPopulator;
	
	@Autowired
	private UserBasicPopulator userBasicPopulator;

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
			
			return modelService.getDto(entity, UserDto.class, new DtoConverterContext(employeeFullPopulator));

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<UserDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<User> page = modelService.findPage(UserSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), User.class);

		List<UserDto> dtos = modelService.getDto(page.getContent(), UserDto.class, new DtoConverterContext(userBasicPopulator));
		return new PageImpl<UserDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(User.class);
	}

}
