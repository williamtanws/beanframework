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

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightService;
import com.beanframework.user.specification.UserRightSpecification;

@Component
public class UserRightFacadeImpl implements UserRightFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserRightService userRightService;

	@Override
	public UserRightDto findOneByUuid(UUID uuid) throws Exception {
		UserRight entity = modelService.findOneByUuid(uuid, UserRight.class);
		UserRightDto dto = modelService.getDto(entity, UserRightDto.class, new DtoConverterContext(ConvertRelationType.ALL));

		return dto;
	}

	@Override
	public UserRightDto findOneProperties(Map<String, Object> properties) throws Exception {
		UserRight entity = modelService.findOneByProperties(properties, UserRight.class);
		UserRightDto dto = modelService.getDto(entity, UserRightDto.class);

		return dto;
	}

	@Override
	public UserRightDto create(UserRightDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public UserRightDto update(UserRightDto model) throws BusinessException {
		return save(model);
	}

	public UserRightDto save(UserRightDto dto) throws BusinessException {
		try {
			UserRight entity = modelService.getEntity(dto, UserRight.class);
			entity = modelService.saveEntity(entity, UserRight.class);

			return modelService.getDto(entity, UserRightDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, UserRight.class);
	}

	@Override
	public Page<UserRightDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<UserRight> page = modelService.findPage(UserRightSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), UserRight.class);

		List<UserRightDto> dtos = modelService.getDto(page.getContent(), UserRightDto.class, new DtoConverterContext(ConvertRelationType.BASIC));
		return new PageImpl<UserRightDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(UserRight.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = userRightService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof UserRight) {

				entityObject[0] = modelService.getDto(entityObject[0], UserRightDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return userRightService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<UserRightDto> findAllDtoUserRights() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserRight.CREATED_DATE, Sort.Direction.DESC);

		List<UserRight> userRights = modelService.findByPropertiesBySortByResult(null, sorts, null, null, UserRight.class);
		return modelService.getDto(userRights, UserRightDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public UserRightDto createDto() throws Exception {
		UserRight userRight = modelService.create(UserRight.class);
		return modelService.getDto(userRight, UserRightDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}
}
