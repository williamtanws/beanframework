package com.beanframework.language.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.language.LanguageConstants;
import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.language.converter.EntityLanguageConverter;
import com.beanframework.language.domain.Language;
import com.beanframework.language.domain.LanguageSpecification;
import com.beanframework.language.repository.LanguageRepository;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private EntityLanguageConverter entityLanguageConverter;

	@Autowired
	private DtoLanguageConverter dtoLanguageConverter;

	@Override
	public Language create() {
		return initDefaults(new Language());
	}

	@Override
	public Language initDefaults(Language language) {
		return language;
	}

	@CacheEvict(value = { LanguageConstants.Cache.LANGUAGE,  LanguageConstants.Cache.LANGUAGES}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public Language save(Language language) {

		language = entityLanguageConverter.convert(language);
		language = languageRepository.save(language);
		language = dtoLanguageConverter.convert(language);

		return language;
	}

	@CacheEvict(value = { LanguageConstants.Cache.LANGUAGE,  LanguageConstants.Cache.LANGUAGES}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		languageRepository.deleteById(uuid);
	}

	@CacheEvict(value = { LanguageConstants.Cache.LANGUAGE,  LanguageConstants.Cache.LANGUAGES}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		languageRepository.deleteAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<Language> findEntityByUuid(UUID uuid) {
		return languageRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Language> findEntityById(String id) {
		return languageRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Language findByUuid(UUID uuid) {
		Optional<Language> language = languageRepository.findByUuid(uuid);

		if (language.isPresent()) {
			return dtoLanguageConverter.convert(language.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Language findById(String id) {
		Optional<Language> language = languageRepository.findById(id);

		if (language.isPresent()) {
			return dtoLanguageConverter.convert(language.get());
		} else {
			return null;
		}
	}

	@Cacheable(LanguageConstants.Cache.LANGUAGES)
	@Transactional(readOnly = true)
	@Override
	public List<Language> findByOrderBySortAsc() {
		return dtoLanguageConverter.convert(languageRepository.findByOrderBySortAsc());
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Language> findEntityAllByOrderBySortAsc() {
		return languageRepository.findByOrderBySortAsc();
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isIdExists(String id) {
		return languageRepository.isIdExists(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Language> page(Language language, Pageable pageable) {
		Page<Language> page = languageRepository.findAll(LanguageSpecification.findByCriteria(language), pageable);
		List<Language> content = dtoLanguageConverter.convert(page.getContent());
		return new PageImpl<Language>(content, page.getPageable(), page.getTotalElements());
	}

	public LanguageRepository getLanguageRepository() {
		return languageRepository;
	}

	public void setLanguageRepository(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}

	public EntityLanguageConverter getEntityLanguageConverter() {
		return entityLanguageConverter;
	}

	public void setEntityLanguageConverter(EntityLanguageConverter entityLanguageConverter) {
		this.entityLanguageConverter = entityLanguageConverter;
	}

	public DtoLanguageConverter getDtoLanguageConverter() {
		return dtoLanguageConverter;
	}

	public void setDtoLanguageConverter(DtoLanguageConverter dtoLanguageConverter) {
		this.dtoLanguageConverter = dtoLanguageConverter;
	}
}
