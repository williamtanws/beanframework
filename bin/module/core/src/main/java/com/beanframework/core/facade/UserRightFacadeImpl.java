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

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightService;

@Component
public class UserRightFacadeImpl implements UserRightFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserRightService userRightService;

	@Override
	public UserRightDto findOneByUuid(UUID uuid) throws Exception {
		UserRight entity = userRightService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, UserRightDto.class);
	}

	@Override
	public UserRightDto findOneProperties(Map<String, Object> properties) throws Exception {
		UserRight entity = userRightService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, UserRightDto.class);
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
			entity = (UserRight) userRightService.saveEntity(entity);

			return modelService.getDto(entity, UserRightDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		userRightService.deleteByUuid(uuid);
	}
	
	@Override
	public Page<UserRightDto> findPage(DataTableRequest<UserRightDto> dataTableRequest) throws Exception {
		Page<UserRight> page = userRightService.findEntityPage(dataTableRequest);
		List<UserRightDto> dtos = modelService.getDto(page.getContent(), UserRightDto.class);
		return new PageImpl<UserRightDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return userRightService.count();
	}
	
	@Override
	public List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {

		List<Object[]> revisions = userRightService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof UserRight)
				entityObject[0] = modelService.getDto(entityObject[0], UserRightDto.class);
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {
		return userRightService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<UserRightDto> findAllDtoUserRights() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserRight.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(userRightService.findEntityBySorts(sorts, false), UserRightDto.class);
	}
}