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

import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.backoffice.data.UserGroupSearch;
import com.beanframework.backoffice.data.UserGroupSpecification;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.service.UserGroupService;

@Component
public class UserGroupFacadeImpl implements UserGroupFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private UserGroupService userGroupService;

	@Override
	public Page<UserGroupDto> findPage(UserGroupSearch search, PageRequest pageable) throws Exception {
		Page<UserGroup> page = userGroupService.findEntityPage(search.toString(), UserGroupSpecification.findByCriteria(search), pageable);
		List<UserGroupDto> dtos = modelService.getDto(page.getContent(), UserGroupDto.class);
		return new PageImpl<UserGroupDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public UserGroupDto findOneByUuid(UUID uuid) throws Exception {
		UserGroup entity = userGroupService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, UserGroupDto.class);
	}

	@Override
	public UserGroupDto findOneProperties(Map<String, Object> properties) throws Exception {
		UserGroup entity = userGroupService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, UserGroupDto.class);
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
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = userGroupService.findHistoryByUuid(uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserGroupDto.class);
//		}

		return revisions;
	}

	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = userGroupService.findHistoryByRelatedUuid(UserGroupField.USER_GROUP, uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserGroupFieldDto.class);
//		}

		return revisions;
	}

	@Override
	public List<UserGroupDto> findAllDtoUserGroups() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserGroupDto.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(userGroupService.findEntityBySorts(sorts), UserGroupDto.class);
	}
}
