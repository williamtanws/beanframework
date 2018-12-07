package com.beanframework.language.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.language.domain.Language;

public interface LanguageFacade {

	Language create();

	Language initDefaults(Language language);

	Language save(Language language, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	Language findByUuid(UUID uuid);

	Language findById(String id);
		
	List<Language> findByOrderBySortAsc();
	
	boolean isIdExists(String id);

	Page<Language> page(Language language, int page, int size, Direction direction, String... properties);
}
