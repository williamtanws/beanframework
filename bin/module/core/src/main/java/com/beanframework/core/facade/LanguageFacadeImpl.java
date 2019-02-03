package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.core.specification.LanguageSpecification;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;

@Component
public class LanguageFacadeImpl implements LanguageFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private LanguageService languageService;

	@Override
	public LanguageDto findOneByUuid(UUID uuid) throws Exception {
		Language entity = languageService.findOneEntityByUuid(uuid);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, LanguageDto.class);
	}

	@Override
	public LanguageDto findOneProperties(Map<String, Object> properties) throws Exception {
		Language entity = languageService.findOneEntityByProperties(properties);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, LanguageDto.class);
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

			InterceptorContext context = new InterceptorContext();
			context.setInitializeCollection(true);
			return modelService.getDto(entity, context, LanguageDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		languageService.deleteByUuid(uuid);
	}
	
	@Override
	public Page<LanguageDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Language> page = languageService.findEntityPage(dataTableRequest, LanguageSpecification.getSpecification(dataTableRequest));
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		List<LanguageDto> dtos = modelService.getDto(page.getContent(), context, LanguageDto.class);
		return new PageImpl<LanguageDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return languageService.count();
	}
	
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = languageService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Language) {
				
				InterceptorContext context = new InterceptorContext();
				context.setInitializeCollection(true);
				entityObject[0] = modelService.getDto(entityObject[0], context, LanguageDto.class);
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
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(languageService.create(), context, LanguageDto.class);
	}
}
