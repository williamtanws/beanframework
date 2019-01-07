package com.beanframework.user.service;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;

@Component
public class UserGroupFacadeImpl implements UserGroupFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<UserGroup> findPage(Specification<UserGroup> specification, PageRequest pageRequest) throws Exception {
		return modelService.findDtoPage(specification, pageRequest, UserGroup.class);
	}

	@Override
	public UserGroup create() throws Exception {
		return modelService.create(UserGroup.class);
	}
	
	@Override
	public UserGroup findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, UserGroup.class);
	}

	@Override
	public UserGroup findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, UserGroup.class);
	}

	@Override
	public UserGroup createDto(UserGroup model) throws BusinessException {
		return (UserGroup) modelService.saveDto(model, UserGroup.class);
	}

	@Override
	public UserGroup updateDto(UserGroup model) throws BusinessException {
		return (UserGroup) modelService.saveDto(model, UserGroup.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, UserGroup.class);
	}

	@Override
	public List<UserGroup> findDtoBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findDtoBySorts(sorts, UserGroup.class);
	}

	@Override
	public UserGroup saveEntity(UserGroup model) throws BusinessException {
		return (UserGroup) modelService.saveEntity(model, UserGroup.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserGroup.ID, id);
			UserGroup model = modelService.findOneEntityByProperties(properties, UserGroup.class);
			modelService.deleteByEntity(model, UserGroup.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserGroup.class);
		
		return revisions;
	}
	
	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(UserGroupField.USER_GROUP).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserGroupField.class);
		
		return revisions;
	}

}
