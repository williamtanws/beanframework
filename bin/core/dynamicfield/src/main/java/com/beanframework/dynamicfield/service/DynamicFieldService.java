package com.beanframework.dynamicfield.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.dynamicfield.domain.DynamicField;

public interface DynamicFieldService {

	DynamicField create();

	DynamicField initDefaults(DynamicField dynamicField);

	DynamicField save(DynamicField dynamicField);

	void delete(UUID uuid);

	void deleteAll();

	Optional<DynamicField> findEntityByUuid(UUID uuid);

	DynamicField findByUuid(UUID uuid);

	Page<DynamicField> page(DynamicField dynamicField, Pageable pageable);

}
