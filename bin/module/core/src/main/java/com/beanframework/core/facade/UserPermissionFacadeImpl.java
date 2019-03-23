package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.specification.UserPermissionSpecification;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.service.UserPermissionService;

@Component
public class UserPermissionFacadeImpl implements UserPermissionFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserPermissionService userPermissionService;
	
	@Autowired
	private DtoConverterContext dtoConverterContext;

	@Override
	public UserPermissionDto findOneByUuid(UUID uuid) throws Exception {
		dtoConverterContext.addFetchProperty(UserPermission.FIELDS);
		UserPermission entity = userPermissionService.findOneEntityByUuid(uuid);
		UserPermissionDto dto = modelService.getDto(entity, UserPermissionDto.class);
		dtoConverterContext.clearFetchProperties();
		
		return dto;
	}

	@Override
	public UserPermissionDto findOneProperties(Map<String, Object> properties) throws Exception {
		dtoConverterContext.addFetchProperty(UserPermission.FIELDS);
		UserPermission entity = userPermissionService.findOneEntityByProperties(properties);
		UserPermissionDto dto = modelService.getDto(entity, UserPermissionDto.class);
		dtoConverterContext.clearFetchProperties();
		
		return dto;
	}

	@Override
	public UserPermissionDto create(UserPermissionDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public UserPermissionDto update(UserPermissionDto model) throws BusinessException {
		return save(model);
	}

	public UserPermissionDto save(UserPermissionDto dto) throws BusinessException {
		try {
			UserPermission entity = modelService.getEntity(dto, UserPermission.class);
			entity = (UserPermission) userPermissionService.saveEntity(entity);

			return modelService.getDto(entity, UserPermissionDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		userPermissionService.deleteByUuid(uuid);
	}

	@Override
	public Page<UserPermissionDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<UserPermission> page = userPermissionService.findEntityPage(dataTableRequest, UserPermissionSpecification.getSpecification(dataTableRequest));

		List<UserPermissionDto> dtos = modelService.getDto(page.getContent(), UserPermissionDto.class);
		return new PageImpl<UserPermissionDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return userPermissionService.count();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = userPermissionService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof UserPermission) {

				entityObject[0] = modelService.getDto(entityObject[0], UserPermissionDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return userPermissionService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<UserPermissionDto> findAllDtoUserPermissions() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserPermission.CREATED_DATE, Sort.Direction.DESC);

		return modelService.getDto(userPermissionService.findEntityBySorts(sorts), UserPermissionDto.class);
	}

	@Override
	public UserPermissionDto createDto() throws Exception {

		return modelService.getDto(userPermissionService.create(), UserPermissionDto.class);
	}

}
