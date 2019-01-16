package com.beanframework.backoffice.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.backoffice.data.LanguageSearch;
import com.beanframework.backoffice.data.LanguageSpecification;
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
	public Page<LanguageDto> findPage(LanguageSearch search, PageRequest pageable) throws Exception {
		Page<Language> page = languageService.findEntityPage(search.toString(), LanguageSpecification.findByCriteria(search), pageable);
		List<LanguageDto> dtos = modelService.getDto(page.getContent(), LanguageDto.class);
		return new PageImpl<LanguageDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public LanguageDto findOneByUuid(UUID uuid) throws Exception {
		Language entity = languageService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, LanguageDto.class);
	}

	@Override
	public LanguageDto findOneProperties(Map<String, Object> properties) throws Exception {
		Language entity = languageService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, LanguageDto.class);
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
		languageService.deleteByUuid(uuid);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = languageService.findHistoryByUuid(uuid, firstResult, maxResults);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], LanguageDto.class);
		}

		return revisions;
	}

	@Override
	public List<LanguageDto> findAllDtoLanguages() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(LanguageDto.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(languageService.findEntityBySorts(sorts), LanguageDto.class);
	}

}
