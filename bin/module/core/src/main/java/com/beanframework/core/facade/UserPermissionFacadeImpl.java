package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.specification.UserPermissionSpecification;

@Component
public class UserPermissionFacadeImpl extends AbstractFacade<UserPermission, UserPermissionDto> implements UserPermissionFacade {
	
	private static final Class<UserPermission> entityClass = UserPermission.class;
	private static final Class<UserPermissionDto> dtoClass = UserPermissionDto.class;

	@Override
	public UserPermissionDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public UserPermissionDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public UserPermissionDto create(UserPermissionDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public UserPermissionDto update(UserPermissionDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<UserPermissionDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, UserPermissionSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
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
	public UserPermissionDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

	@Override
	public List<UserPermissionDto> findAllDtoUserPermissions() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserPermission.CREATED_DATE, Sort.Direction.DESC);

		List<UserPermission> userPermissions = modelService.findByPropertiesBySortByResult(null, sorts, null, null, UserPermission.class);
		return modelService.getDtoList(userPermissions, dtoClass);
	}
}
