package com.beanframework.backoffice.facade;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.AuditorDto;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.service.ModelService;

@Component
public class AuditorFacadeImpl implements AuditorFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<AuditorDto> findDtoPage(Specification<AuditorDto> findByCriteria, PageRequest pageable) throws Exception {
		Page<Auditor> page = modelService.findEntityPage(findByCriteria, pageable, Auditor.class);
		List<AuditorDto> dtos = modelService.getDto(page.getContent(), AuditorDto.class);
		return new PageImpl<AuditorDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public AuditorDto findOneDtoByUuid(UUID uuid) throws Exception {
		Auditor entity = modelService.findOneEntityByUuid(uuid, Auditor.class);
		return modelService.getDto(entity, AuditorDto.class);
	}

	@Override
	public AuditorDto findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		Auditor entity = modelService.findOneEntityByProperties(properties, AuditorDto.class);
		return modelService.getDto(entity, AuditorDto.class);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Auditor.class);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], AuditorDto.class);
		}

		return revisions;
	}

}
