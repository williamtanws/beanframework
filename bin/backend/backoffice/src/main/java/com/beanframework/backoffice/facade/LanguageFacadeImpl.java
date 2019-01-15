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

import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;

@Component
public class LanguageFacadeImpl implements LanguageFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private LanguageService languageService;

	@Override
	public Page<LanguageDto> findPage(Specification<LanguageDto> specification, PageRequest pageable) throws Exception {
		Page<Language> page = modelService.findEntityPage(specification, pageable, Language.class);
		List<LanguageDto> dtos = modelService.getDto(page.getContent(), LanguageDto.class);
		return new PageImpl<LanguageDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public LanguageDto findOneByUuid(UUID uuid) throws Exception {
		Language entity = modelService.findOneEntityByUuid(uuid, Language.class);
		return modelService.getDto(entity, LanguageDto.class);
	}

	@Override
	public LanguageDto findOneByProperties(Map<String, Object> properties) throws Exception {
		Language entity = modelService.findOneEntityByProperties(properties, Language.class);
		return modelService.getDto(entity, LanguageDto.class);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Language.class);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], LanguageDto.class);
		}

		return revisions;
	}

	@Override
	public LanguageDto create(LanguageDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public LanguageDto update(LanguageDto model) throws BusinessException {
		return save(model);
	}

	public LanguageDto save(LanguageDto dto) throws BusinessException {
		try {
			Language entity = modelService.getEntity(dto, Language.class);
			entity = (Language) languageService.saveEntity(entity);

			return modelService.getDto(entity, LanguageDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
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
