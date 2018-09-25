package com.beanframework.catalog.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.catalog.domain.Catalog;

public interface CatalogService {

	Catalog create();

	Catalog initDefaults(Catalog catalog);

	Catalog save(Catalog catalog);

	void delete(UUID uuid);

	void deleteAll();

	Optional<Catalog> findEntityByUuid(UUID uuid);

	Optional<Catalog> findEntityById(String id);

	Catalog findByUuid(UUID uuid);

	Catalog findById(String id);

	boolean isIdExists(String id);

	Page<Catalog> page(Catalog catalog, Pageable pageable);

}
