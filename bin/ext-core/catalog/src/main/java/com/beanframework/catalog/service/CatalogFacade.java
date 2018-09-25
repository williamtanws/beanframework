package com.beanframework.catalog.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.catalog.domain.Catalog;

public interface CatalogFacade {

	Catalog create();

	Catalog initDefaults(Catalog catalog);

	Catalog save(Catalog catalog, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	Catalog findByUuid(UUID uuid);

	Catalog findById(String id);
	
	boolean isIdExists(String id);

	Page<Catalog> page(Catalog catalog, int page, int size, Direction direction, String... properties);
}
