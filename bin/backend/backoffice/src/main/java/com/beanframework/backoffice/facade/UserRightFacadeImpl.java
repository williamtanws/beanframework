package com.beanframework.backoffice.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.UserRightDto;
import com.beanframework.backoffice.data.UserRightSearch;
import com.beanframework.backoffice.data.UserRightSpecification;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;
import com.beanframework.user.service.UserRightService;

@Component
public class UserRightFacadeImpl implements UserRightFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserRightService userRightService;

	@Override
	public Page<UserRightDto> findPage(UserRightSearch search, PageRequest pageable) throws Exception {
		Page<UserRight> page = userRightService.findEntityPage(search.toString(), UserRightSpecification.findByCriteria(search), pageable);
		List<UserRightDto> dtos = modelService.getDto(page.getContent(), UserRightDto.class);
		return new PageImpl<UserRightDto>(dtos, page.getPageable(), page.getTotalElements());
	}

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
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = userRightService.findHistoryByUuid(uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserRightDto.class);
//		}

		return revisions;
	}

	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = userRightService.findHistoryByRelatedUuid(UserRightField.USER_RIGHT, uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserRightFieldDto.class);
//		}

		return revisions;
	}

	@Override
	public List<UserRightDto> findAllDtoUserRights() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserRightDto.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(userRightService.findEntityBySorts(sorts), UserRightDto.class);
	}
}
