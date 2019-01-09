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
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

@Component
public class UserRightFacadeImpl implements UserRightFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<UserRight> findPage(Specification<UserRight> specification, PageRequest pageRequest) throws Exception {
		return modelService.findDtoPage(specification, pageRequest, UserRight.class);
	}

	@Override
	public UserRight create() throws Exception {
		return modelService.create(UserRight.class);
	}

	@Override
	public UserRight findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, UserRight.class);
	}

	@Override
	public UserRight findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, UserRight.class);
	}

	@Override
	public UserRight createDto(UserRight model) throws BusinessException {
		return (UserRight) modelService.saveDto(model, UserRight.class);
	}

	@Override
	public UserRight updateDto(UserRight model) throws BusinessException {
		return (UserRight) modelService.saveDto(model, UserRight.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, UserRight.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<UserRight> findDtoBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findDtoBySorts(sorts, UserRight.class);
	}

	@Override
	public UserRight saveEntity(UserRight model) throws BusinessException {
		return (UserRight) modelService.saveEntity(model, UserRight.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserRight.ID, id);
			UserRight model = modelService.findOneEntityByProperties(properties, UserRight.class);
			modelService.deleteByEntity(model, UserRight.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserRight.class);
		
		return revisions;
	}
	
	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(UserRightField.USER_RIGHT).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserRightField.class);
		
		return revisions;
	}

}
