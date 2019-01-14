package com.beanframework.language.service;

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
import com.beanframework.language.domain.Language;

@Component
public class LanguageFacadeImpl implements LanguageFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<Language> findDtoPage(Specification<Language> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, Language.class);
	}

	@Override
	public Language findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, Language.class);
	}

	@Override
	public Language findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, Language.class);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Language.class);

		return revisions;
	}

	@Override
	public Language createDto(Language model) throws BusinessException {
		return (Language) modelService.saveDto(model, Language.class);
	}

	@Override
	public Language updateDto(Language model) throws BusinessException {
		return (Language) modelService.saveDto(model, Language.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, Language.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}



}
