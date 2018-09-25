package com.beanframework.catalog.service;

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

import com.beanframework.catalog.CatalogConstants;
import com.beanframework.catalog.converter.DtoCatalogConverter;
import com.beanframework.catalog.converter.EntityCatalogConverter;
import com.beanframework.catalog.domain.Catalog;
import com.beanframework.catalog.domain.CatalogSpecification;
import com.beanframework.catalog.repository.CatalogRepository;

@Service
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogRepository catalogRepository;

	@Autowired
	private EntityCatalogConverter entityCatalogConverter;

	@Autowired
	private DtoCatalogConverter dtoCatalogConverter;

	@Override
	public Catalog create() {
		return initDefaults(new Catalog());
	}

	@Override
	public Catalog initDefaults(Catalog catalog) {
		return catalog;
	}

	@CacheEvict(value = { CatalogConstants.Cache.CATALOG,  CatalogConstants.Cache.CATALOGS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public Catalog save(Catalog catalog) {

		catalog = entityCatalogConverter.convert(catalog);
		catalog = catalogRepository.save(catalog);
		catalog = dtoCatalogConverter.convert(catalog);

		return catalog;
	}

	@CacheEvict(value = { CatalogConstants.Cache.CATALOG,  CatalogConstants.Cache.CATALOGS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		catalogRepository.deleteById(uuid);
	}

	@CacheEvict(value = { CatalogConstants.Cache.CATALOG,  CatalogConstants.Cache.CATALOGS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		catalogRepository.deleteAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<Catalog> findEntityByUuid(UUID uuid) {
		return catalogRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Catalog> findEntityById(String id) {
		return catalogRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Catalog findByUuid(UUID uuid) {
		Optional<Catalog> catalog = catalogRepository.findByUuid(uuid);

		if (catalog.isPresent()) {
			return dtoCatalogConverter.convert(catalog.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Catalog findById(String id) {
		Optional<Catalog> catalog = catalogRepository.findById(id);

		if (catalog.isPresent()) {
			return dtoCatalogConverter.convert(catalog.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isIdExists(String id) {
		return catalogRepository.isIdExists(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Catalog> page(Catalog catalog, Pageable pageable) {
		Page<Catalog> page = catalogRepository.findAll(CatalogSpecification.findByCriteria(catalog), pageable);
		List<Catalog> content = dtoCatalogConverter.convert(page.getContent());
		return new PageImpl<Catalog>(content, page.getPageable(), page.getTotalElements());
	}

	public CatalogRepository getCatalogRepository() {
		return catalogRepository;
	}

	public void setCatalogRepository(CatalogRepository catalogRepository) {
		this.catalogRepository = catalogRepository;
	}

	public EntityCatalogConverter getEntityCatalogConverter() {
		return entityCatalogConverter;
	}

	public void setEntityCatalogConverter(EntityCatalogConverter entityCatalogConverter) {
		this.entityCatalogConverter = entityCatalogConverter;
	}

	public DtoCatalogConverter getDtoCatalogConverter() {
		return dtoCatalogConverter;
	}

	public void setDtoCatalogConverter(DtoCatalogConverter dtoCatalogConverter) {
		this.dtoCatalogConverter = dtoCatalogConverter;
	}
}
