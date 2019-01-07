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
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

@Component
public class UserPermissionFacadeImpl implements UserPermissionFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<UserPermission> findPage(Specification<UserPermission> specification, PageRequest pageRequest) throws Exception {
		return modelService.findDtoPage(specification, pageRequest, UserPermission.class);
	}

	@Override
	public UserPermission create() throws Exception {
		return modelService.create(UserPermission.class);
	}
	

	@Override
	public UserPermission findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, UserPermission.class);
	}

	@Override
	public UserPermission findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, UserPermission.class);
	}

	@Override
	public UserPermission createDto(UserPermission model) throws BusinessException {
		return (UserPermission) modelService.saveDto(model, UserPermission.class);
	}

	@Override
	public UserPermission updateDto(UserPermission model) throws BusinessException {
		return (UserPermission) modelService.saveDto(model, UserPermission.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, UserPermission.class);
	}

	@Override
	public List<UserPermission> findDtoBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findDtoBySorts(sorts, UserPermission.class);
	}

	@Override
	public UserPermission saveEntity(UserPermission model) throws BusinessException {
		return (UserPermission) modelService.saveEntity(model, UserPermission.class);
	}

	@Override
	public void deleteById(String id) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(UserPermission.ID, id);
		UserPermission model = modelService.findOneEntityByProperties(properties, UserPermission.class);
		modelService.deleteByEntity(model, UserPermission.class);
	}
	
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserPermission.class);
		
		return revisions;
	}
	
	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(UserPermissionField.USER_PERMISSION).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserPermissionField.class);
		
		return revisions;
	}

}
