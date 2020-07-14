package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.internationalization.domain.Language;
import com.beanframework.internationalization.service.LanguageService;
import com.beanframework.internationalization.specification.LanguageSpecification;

@Component
public class LanguageFacadeImpl implements LanguageFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LanguageService languageService;

	@Override
	public LanguageDto findOneByUuid(UUID uuid) throws Exception {
		Language entity = modelService.findOneByUuid(uuid, Language.class);
		return modelService.getDto(entity, LanguageDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public LanguageDto findOneProperties(Map<String, Object> properties) throws Exception {
		Language entity = modelService.findOneByProperties(properties, Language.class);
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
			entity = modelService.saveEntity(entity, Language.class);

			return modelService.getDto(entity, LanguageDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Language.class);
	}

	@Override
	public Page<LanguageDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Language> page = modelService.findPage(LanguageSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Language.class);

		List<LanguageDto> dtos = modelService.getDto(page.getContent(), LanguageDto.class, new DtoConverterContext(ConvertRelationType.BASIC));
		return new PageImpl<LanguageDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Language.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = languageService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Language) {

				entityObject[0] = modelService.getDto(entityObject[0], LanguageDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return languageService.findCountHistory(dataTableRequest);
	}

	@Override
	public LanguageDto createDto() throws Exception {
		Language language = modelService.create(Language.class);
		return modelService.getDto(language, LanguageDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}
}
