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

import com.beanframework.backoffice.data.UserPermissionDto;
import com.beanframework.backoffice.data.UserPermissionSearch;
import com.beanframework.backoffice.data.UserPermissionSpecification;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.service.UserPermissionService;

@Component
public class UserPermissionFacadeImpl implements UserPermissionFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserPermissionService userPermissionService;

	@Override
	public Page<UserPermissionDto> findPage(UserPermissionSearch search, PageRequest pageable) throws Exception {
		Page<UserPermission> page = userPermissionService.findEntityPage(search.toString(), UserPermissionSpecification.findByCriteria(search), pageable);
		List<UserPermissionDto> dtos = modelService.getDto(page.getContent(), UserPermissionDto.class);
		return new PageImpl<UserPermissionDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public UserPermissionDto findOneByUuid(UUID uuid) throws Exception {
		UserPermission entity = userPermissionService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, UserPermissionDto.class);
	}

	@Override
	public UserPermissionDto findOneProperties(Map<String, Object> properties) throws Exception {
		UserPermission entity = userPermissionService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, UserPermissionDto.class);
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
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = userPermissionService.findHistoryByUuid(uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserPermissionDto.class);
//		}

		return revisions;
	}

	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = userPermissionService.findHistoryByRelatedUuid(UserPermissionField.USER_PERMISSION, uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserPermissionFieldDto.class);
//		}

		return revisions;
	}

	@Override
	public List<UserPermissionDto> findAllDtoUserPermissions() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserPermission.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(userPermissionService.findEntityBySorts(sorts, false), UserPermissionDto.class);
	}

}
