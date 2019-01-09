package com.beanframework.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;

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
	public Auditor save(User user) throws BusinessException {
		try {
			Auditor auditor = modelService.findOneEntityByUuid(user.getUuid(), Auditor.class);

			if (auditor == null) {
				auditor = modelService.create(Auditor.class);
				auditor.setUuid(user.getUuid());
				auditor.setId(user.getId());
				auditor.setName(user.getName());
			} else {
				Date lastModifiedDate = new Date();
				if (StringUtils.isNotBlank(user.getId()) && StringUtils.equals(user.getId(), auditor.getId()) == false) {
					auditor.setId(user.getId());
					auditor.setLastModifiedDate(lastModifiedDate);
				}

				if (StringUtils.equals(user.getName(), auditor.getName()) == false) {
					auditor.setName(user.getName());
					auditor.setLastModifiedDate(lastModifiedDate);
				}
			}

			modelService.saveEntity(auditor, Auditor.class);

			return auditor;

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Auditor findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, Auditor.class);
	}

	@Override
	public Auditor create() throws Exception {
		return modelService.create(Auditor.class);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Auditor.class);

		return revisions;
	}

}
