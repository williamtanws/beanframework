package com.beanframework.language.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.language.domain.Language;
import com.beanframework.language.validator.DeleteLanguageValidator;
import com.beanframework.language.validator.SaveLanguageValidator;

@Component
public class LanguageFacadeImpl implements LanguageFacade {

	@Autowired
	private LanguageService languageService;

	@Autowired
	private SaveLanguageValidator saveLanguageValidator;
	
	@Autowired
	private DeleteLanguageValidator deleteLanguageValidator;

	@Override
	public Language create() {
		return languageService.create();
	}

	@Override
	public Language initDefaults(Language language) {
		return languageService.initDefaults(language);
	}

	@Override
	public Language save(Language language, Errors bindingResult) {
		saveLanguageValidator.validate(language, bindingResult);

		if (bindingResult.hasErrors()) {
			return language;
		}

		return languageService.save(language);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteLanguageValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			languageService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		languageService.deleteAll();
	}

	@Override
	public Language findByUuid(UUID uuid) {
		return languageService.findByUuid(uuid);
	}

	@Override
	public Language findById(String id) {
		return languageService.findById(id);
	}

	@Override
	public List<Language> findByOrderBySortAsc() {
		return languageService.findByOrderBySortAsc();
	}

	@Override
	public boolean isIdExists(String id) {
		return languageService.isIdExists(id);
	}

	@Override
	public Page<Language> page(Language language, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return languageService.page(language, pageRequest);
	}

	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	public SaveLanguageValidator getSaveLanguageValidator() {
		return saveLanguageValidator;
	}

	public void setSaveLanguageValidator(SaveLanguageValidator saveLanguageValidator) {
		this.saveLanguageValidator = saveLanguageValidator;
	}

}
