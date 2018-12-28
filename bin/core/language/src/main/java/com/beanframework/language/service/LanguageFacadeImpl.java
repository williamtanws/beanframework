package com.beanframework.language.service;

import java.util.Map;
import java.util.UUID;

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
	public Page<Language> findPage(Specification<Language> specification, PageRequest pageable) throws Exception {
		return modelService.findDtoPage(specification, pageable, Language.class);
	}

	@Override
	public Language create() throws Exception {
		return modelService.create(Language.class);
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
	public Language createDto(Language model) throws BusinessException {
		return (Language) modelService.saveDto(model, Language.class);
	}

	@Override
	public Language updateDto(Language model) throws BusinessException {
		return (Language) modelService.saveDto(model, Language.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.delete(uuid, Language.class);
	}

}
