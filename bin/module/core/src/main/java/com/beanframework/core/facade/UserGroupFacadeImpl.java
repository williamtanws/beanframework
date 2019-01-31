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
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.specification.UserGroupSpecification;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;

@Component
public class UserGroupFacadeImpl implements UserGroupFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserGroupService userGroupService;

	@Override
	public UserGroupDto findOneByUuid(UUID uuid) throws Exception {
		UserGroup entity = userGroupService.findOneEntityByUuid(uuid);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, UserGroupDto.class);
	}

	@Override
	public UserGroupDto findOneProperties(Map<String, Object> properties) throws Exception {
		UserGroup entity = userGroupService.findOneEntityByProperties(properties);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, UserGroupDto.class);
	}

	@Override
	public UserGroupDto create(UserGroupDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public UserGroupDto update(UserGroupDto model) throws BusinessException {
		return save(model);
	}

	public UserGroupDto save(UserGroupDto dto) throws BusinessException {
		try {
			UserGroup entity = modelService.getEntity(dto, UserGroup.class);
			entity = (UserGroup) userGroupService.saveEntity(entity);

			InterceptorContext context = new InterceptorContext();
			context.setInitializeCollection(true);
			return modelService.getDto(entity, context, UserGroupDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		userGroupService.deleteByUuid(uuid);
	}

	@Override
	public Page<UserGroupDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<UserGroup> page = userGroupService.findEntityPage(dataTableRequest, UserGroupSpecification.getSpecification(dataTableRequest));
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		List<UserGroupDto> dtos = modelService.getDto(page.getContent(), context, UserGroupDto.class);
		return new PageImpl<UserGroupDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return userGroupService.count();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = userGroupService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof UserGroup) {

				InterceptorContext context = new InterceptorContext();
				context.setInitializeCollection(true);
				entityObject[0] = modelService.getDto(entityObject[0], context, UserGroupDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return userGroupService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<UserGroupDto> findAllDtoUserGroups() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserGroup.CREATED_DATE, Sort.Direction.DESC);

		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		return modelService.getDto(userGroupService.findEntityBySorts(sorts, false), context, UserGroupDto.class);
	}
}
