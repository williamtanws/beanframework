package com.beanframework.dynamicfield.service;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;

@Component
public class DynamicFieldFacadeImpl implements DynamicFieldFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<DynamicField> findDtoPage(Specification<DynamicField> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, DynamicField.class);
	}

	@Override
	public DynamicField findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, DynamicField.class);
	}

	@Override
	public DynamicField createDto(DynamicField model) throws BusinessException {
		return (DynamicField) modelService.saveDto(model, DynamicField.class);
	}

	@Override
	public DynamicField updateDto(DynamicField model) throws BusinessException {
		return (DynamicField) modelService.saveDto(model, DynamicField.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, DynamicField.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public DynamicField findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, DynamicField.class);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, DynamicField.class);

		return revisions;
	}

}
