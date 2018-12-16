package com.beanframework.language.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.language.domain.LanguageSpecification;

@Component
public class LanguageFacadeImpl implements LanguageFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<Language> page(Language Language, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		Page<Language> LanguagePage = modelService.findPage(LanguageSpecification.findByCriteria(Language), pageRequest, Language.class);
		
		List<Language> content = modelService.getDto(LanguagePage.getContent());
		return new PageImpl<Language>(content, LanguagePage.getPageable(), LanguagePage.getTotalElements());
	}
}
