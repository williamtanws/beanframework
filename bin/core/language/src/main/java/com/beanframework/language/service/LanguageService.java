package com.beanframework.language.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.language.domain.Language;

public interface LanguageService {

	Language create();

	Language initDefaults(Language language);

	Language save(Language language);

	void delete(UUID uuid);

	void deleteAll();

	Optional<Language> findEntityByUuid(UUID uuid);

	Optional<Language> findEntityById(String id);

	Language findByUuid(UUID uuid);

	Language findById(String id);

	List<Language> findByOrderBySortAsc();

	List<Language> findEntityAllByOrderBySortAsc();

	boolean isIdExists(String id);

	Page<Language> page(Language language, Pageable pageable);

}
