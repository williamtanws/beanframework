package com.beanframework.dynamicfield.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.dynamicfield.domain.DynamicField;

public interface DynamicFieldFacade {

	DynamicField create();

	DynamicField initDefaults(DynamicField dynamicField);

	DynamicField save(DynamicField dynamicField, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	DynamicField findByUuid(UUID uuid);
	
	Page<DynamicField> page(DynamicField dynamicField, int page, int size, Direction direction, String... properties);
}
