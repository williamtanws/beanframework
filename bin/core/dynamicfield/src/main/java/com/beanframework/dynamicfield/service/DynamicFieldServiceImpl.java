package com.beanframework.dynamicfield.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.dynamicfield.DynamicFieldConstants;
import com.beanframework.dynamicfield.converter.DtoDynamicFieldConverter;
import com.beanframework.dynamicfield.converter.EntityDynamicFieldConverter;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSpecification;
import com.beanframework.dynamicfield.repository.DynamicFieldRepository;

@Service
public class DynamicFieldServiceImpl implements DynamicFieldService {

	@Autowired
	private DynamicFieldRepository dynamicFieldRepository;

	@Autowired
	private EntityDynamicFieldConverter entityDynamicFieldConverter;

	@Autowired
	private DtoDynamicFieldConverter dtoDynamicFieldConverter;

	@Override
	public DynamicField create() {
		return initDefaults(new DynamicField());
	}

	@Override
	public DynamicField initDefaults(DynamicField dynamicField) {
		return dynamicField;
	}

	@CacheEvict(value = { DynamicFieldConstants.Cache.DYNAMIC_FIELD,  DynamicFieldConstants.Cache.DYNAMIC_FIELDS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public DynamicField save(DynamicField dynamicField) {

		dynamicField = entityDynamicFieldConverter.convert(dynamicField);
		dynamicField = dynamicFieldRepository.save(dynamicField);
		dynamicField = dtoDynamicFieldConverter.convert(dynamicField);

		return dynamicField;
	}

	@CacheEvict(value = { DynamicFieldConstants.Cache.DYNAMIC_FIELD,  DynamicFieldConstants.Cache.DYNAMIC_FIELDS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		dynamicFieldRepository.deleteById(uuid);
	}

	@CacheEvict(value = { DynamicFieldConstants.Cache.DYNAMIC_FIELD,  DynamicFieldConstants.Cache.DYNAMIC_FIELDS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		dynamicFieldRepository.deleteAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<DynamicField> findEntityByUuid(UUID uuid) {
		return dynamicFieldRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public DynamicField findByUuid(UUID uuid) {
		Optional<DynamicField> dynamicField = dynamicFieldRepository.findByUuid(uuid);

		if (dynamicField.isPresent()) {
			return dtoDynamicFieldConverter.convert(dynamicField.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<DynamicField> page(DynamicField dynamicField, Pageable pageable) {
		Page<DynamicField> page = dynamicFieldRepository.findAll(DynamicFieldSpecification.findByCriteria(dynamicField), pageable);
		List<DynamicField> content = dtoDynamicFieldConverter.convert(page.getContent());
		return new PageImpl<DynamicField>(content, page.getPageable(), page.getTotalElements());
	}

	public DynamicFieldRepository getDynamicFieldRepository() {
		return dynamicFieldRepository;
	}

	public void setDynamicFieldRepository(DynamicFieldRepository dynamicFieldRepository) {
		this.dynamicFieldRepository = dynamicFieldRepository;
	}

	public EntityDynamicFieldConverter getEntityDynamicFieldConverter() {
		return entityDynamicFieldConverter;
	}

	public void setEntityDynamicFieldConverter(EntityDynamicFieldConverter entityDynamicFieldConverter) {
		this.entityDynamicFieldConverter = entityDynamicFieldConverter;
	}

	public DtoDynamicFieldConverter getDtoDynamicFieldConverter() {
		return dtoDynamicFieldConverter;
	}

	public void setDtoDynamicFieldConverter(DtoDynamicFieldConverter dtoDynamicFieldConverter) {
		this.dtoDynamicFieldConverter = dtoDynamicFieldConverter;
	}
}
