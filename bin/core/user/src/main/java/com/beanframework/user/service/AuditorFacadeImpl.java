package com.beanframework.user.service;

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

import com.beanframework.common.domain.Auditor;
import com.beanframework.common.service.ModelService;

@Component
public class AuditorFacadeImpl implements AuditorFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<Auditor> findDtoPage(Specification<Auditor> findByCriteria, PageRequest pageable) throws Exception {
		return modelService.findDtoPage(findByCriteria, pageable, Auditor.class);
	}

	@Override
	public Auditor findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, Auditor.class);
	}

	@Override
	public Auditor findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, Auditor.class);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Auditor.class);

		return revisions;
	}

}
