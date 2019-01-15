package com.beanframework.backoffice.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.backoffice.data.UserGroupFieldDto;
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
	public Page<UserGroupDto> findPage(Specification<UserGroupDto> specification, PageRequest pageable) throws Exception {
		Page<UserGroup> page = modelService.findEntityPage(specification, pageable, UserGroup.class);
		List<UserGroupDto> dtos = modelService.getDto(page.getContent(), UserGroupDto.class);
		return new PageImpl<UserGroupDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public UserGroupDto findOneByUuid(UUID uuid) throws Exception {
		UserGroup entity = modelService.findOneEntityByUuid(uuid, UserGroup.class);
		return modelService.getDto(entity, UserGroupDto.class);
	}

	@Override
	public UserGroupDto findOneByProperties(Map<String, Object> properties) throws Exception {
		UserGroup entity = modelService.findOneEntityByProperties(properties, UserGroup.class);
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
		try {
			modelService.deleteByUuid(uuid, UserGroup.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserGroup.class);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserGroupDto.class);
		}

		return revisions;
	}

	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(UserGroupField.USER_GROUP).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserGroupField.class);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserGroupFieldDto.class);
		}


		return revisions;
	}

	@Override
	public List<UserGroupDto> findAllDtoUserGroups() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserGroup.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(userGroupService.findEntityBySorts(sorts), UserGroupDto.class);
	}
}
