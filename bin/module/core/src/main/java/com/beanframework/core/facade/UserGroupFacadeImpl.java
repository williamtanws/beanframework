package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.specification.UserGroupSpecification;
import com.beanframework.user.domain.UserGroup;

@Component
public class UserGroupFacadeImpl extends AbstractFacade<UserGroup, UserGroupDto> implements UserGroupFacade {
	
	private static final Class<UserGroup> entityClass = UserGroup.class;
	private static final Class<UserGroupDto> dtoClass = UserGroupDto.class;

	@Override
	public UserGroupDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public UserGroupDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public UserGroupDto create(UserGroupDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public UserGroupDto update(UserGroupDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<UserGroupDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, UserGroupSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass, dtoClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public UserGroupDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}
}
