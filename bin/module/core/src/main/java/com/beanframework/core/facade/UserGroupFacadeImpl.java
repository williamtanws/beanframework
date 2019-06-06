package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;
import com.beanframework.user.specification.UserGroupSpecification;

@Component
public class UserGroupFacadeImpl implements UserGroupFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserGroupService userGroupService;

	@Override
	public UserGroupDto findOneByUuid(UUID uuid) throws Exception {
		UserGroup entity = modelService.findOneByUuid(uuid, UserGroup.class);
		UserGroupDto dto = modelService.getDto(entity, UserGroupDto.class, new DtoConverterContext(ConvertRelationType.ALL));

		return dto;
	}

	@Override
	public UserGroupDto findOneProperties(Map<String, Object> properties) throws Exception {
		UserGroup entity = modelService.findOneByProperties(properties, UserGroup.class);
		UserGroupDto dto = modelService.getDto(entity, UserGroupDto.class);

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
			entity = modelService.saveEntity(entity, UserGroup.class);

			return modelService.getDto(entity, UserGroupDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, UserGroup.class);
	}

	@Override
	public Page<UserGroupDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<UserGroup> page = modelService.findPage(UserGroupSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), UserGroup.class);

		List<UserGroupDto> dtos = modelService.getDto(page.getContent(), UserGroupDto.class, new DtoConverterContext(ConvertRelationType.BASIC));
		return new PageImpl<UserGroupDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(UserGroup.class);
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
	public UserGroupDto createDto() throws Exception {
		UserGroup userGroup = modelService.create(UserGroup.class);
		return modelService.getDto(userGroup, UserGroupDto.class);
	}
}
