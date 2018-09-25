package com.beanframework.catalog.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.catalog.domain.Catalog;
import com.beanframework.catalog.validator.DeleteCatalogValidator;
import com.beanframework.catalog.validator.SaveCatalogValidator;

@Component
public class CatalogFacadeImpl implements CatalogFacade {

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private SaveCatalogValidator saveCatalogValidator;
	
	@Autowired
	private DeleteCatalogValidator deleteCatalogValidator;

	@Override
	public Catalog create() {
		return catalogService.create();
	}

	@Override
	public Catalog initDefaults(Catalog catalog) {
		return catalogService.initDefaults(catalog);
	}

	@Override
	public Catalog save(Catalog catalog, Errors bindingResult) {
		saveCatalogValidator.validate(catalog, bindingResult);

		if (bindingResult.hasErrors()) {
			return catalog;
		}

		return catalogService.save(catalog);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteCatalogValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			catalogService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		catalogService.deleteAll();
	}

	@Override
	public Catalog findByUuid(UUID uuid) {
		return catalogService.findByUuid(uuid);
	}

	@Override
	public Catalog findById(String id) {
		return catalogService.findById(id);
	}

	@Override
	public boolean isIdExists(String id) {
		return catalogService.isIdExists(id);
	}

	@Override
	public Page<Catalog> page(Catalog catalog, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return catalogService.page(catalog, pageRequest);
	}

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public SaveCatalogValidator getSaveCatalogValidator() {
		return saveCatalogValidator;
	}

	public void setSaveCatalogValidator(SaveCatalogValidator saveCatalogValidator) {
		this.saveCatalogValidator = saveCatalogValidator;
	}

}
