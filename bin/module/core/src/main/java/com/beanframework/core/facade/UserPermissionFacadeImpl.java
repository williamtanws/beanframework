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

import com.beanframework.common.converter.InterceptorContext;
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

	@Override
	public UserPermissionDto findOneByUuid(UUID uuid) throws Exception {
		UserPermission entity = userPermissionService.findOneEntityByUuid(uuid);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, UserPermissionDto.class);
	}

	@Override
	public UserPermissionDto findOneProperties(Map<String, Object> properties) throws Exception {
		UserPermission entity = userPermissionService.findOneEntityByProperties(properties);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, UserPermissionDto.class);
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

			InterceptorContext context = new InterceptorContext();
			context.setInitializeCollection(true);
			return modelService.getDto(entity, context, UserPermissionDto.class);
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
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		List<UserPermissionDto> dtos = modelService.getDto(page.getContent(), context, UserPermissionDto.class);
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
				
				InterceptorContext context = new InterceptorContext();
				context.setInitializeCollection(false);
				entityObject[0] = modelService.getDto(entityObject[0], context, UserPermissionDto.class);
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
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		return modelService.getDto(userPermissionService.findEntityBySorts(sorts, false), context, UserPermissionDto.class);
	}



}
