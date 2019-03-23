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
	
	@Autowired
	private DtoConverterContext dtoConverterContext;
	
	@Override
	public UserGroupDto findOneByUuid(UUID uuid) throws Exception {
		dtoConverterContext.addFetchProperty(UserGroup.USER_GROUPS);
		dtoConverterContext.addFetchProperty(UserGroup.USER_AUTHORITIES);
		dtoConverterContext.addFetchProperty(UserGroup.FIELDS);
		UserGroup entity = userGroupService.findOneEntityByUuid(uuid);
		UserGroupDto dto = modelService.getDto(entity, UserGroupDto.class);
		dtoConverterContext.clearFetchProperties();
		
		return dto;
	}

	@Override
	public UserGroupDto findOneProperties(Map<String, Object> properties) throws Exception {
		dtoConverterContext.addFetchProperty(UserGroup.USER_GROUPS);
		dtoConverterContext.addFetchProperty(UserGroup.USER_AUTHORITIES);
		dtoConverterContext.addFetchProperty(UserGroup.FIELDS);
		UserGroup entity = userGroupService.findOneEntityByProperties(properties);
		UserGroupDto dto = modelService.getDto(entity, UserGroupDto.class);
		dtoConverterContext.clearFetchProperties();
		
		return dto;
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

			return modelService.getDto(entity, UserGroupDto.class);
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

		List<UserGroupDto> dtos = modelService.getDto(page.getContent(), UserGroupDto.class);
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

				entityObject[0] = modelService.getDto(entityObject[0], UserGroupDto.class);
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

		return modelService.getDto(userGroupService.findEntityBySorts(sorts), UserGroupDto.class);
	}

	@Override
	public UserGroupDto createDto() throws Exception {

		return modelService.getDto(userGroupService.create(), UserGroupDto.class);
	}
}
