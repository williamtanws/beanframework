package com.beanframework.configuration.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	private ModelService modelService;

	@Override
	public Configuration create() throws Exception {
		return modelService.create(Configuration.class);
	}

	@Cacheable(value = "ConfigurationOne", key = "#uuid")
	@Override
	public Configuration findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Configuration.class);
	}

	@Cacheable(value = "ConfigurationOneProperties", key = "#properties")
	@Override
	public Configuration findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Configuration.class);
	}

	@Cacheable(value = "ConfigurationsAll")
	@Override
	public List<Configuration> findAllEntity() throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, null, null, null, Configuration.class);
	}

	@Cacheable(value = "ConfigurationsSorts", key = "#sorts")
	@Override
	public List<Configuration> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Configuration.class);
	}

	@Cacheable(value = "ConfigurationsPage", key = "#query")
	@Override
	public <T> Page<Configuration> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, Configuration.class);
	}

	@Cacheable(value = "ConfigurationsHistory", key = "'uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, Configuration.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "ConfigurationOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "ConfigurationOneProperties", allEntries = true), //
			@CacheEvict(value = "ConfigurationsAll", allEntries = true), //
			@CacheEvict(value = "ConfigurationsSorts", allEntries = true), //
			@CacheEvict(value = "ConfigurationsPage", allEntries = true), //
			@CacheEvict(value = "ConfigurationsHistory", allEntries = true), //
			@CacheEvict(value = "ConfigurationsRelatedHistory", allEntries = true) }) //
	@Override
	public Configuration saveEntity(Configuration model) throws BusinessException {
		return (Configuration) modelService.saveEntity(model, Configuration.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "ConfigurationOne", key = "#uuid"), //
			@CacheEvict(value = "ConfigurationOneProperties", allEntries = true), //
			@CacheEvict(value = "ConfigurationsAll", allEntries = true), //
			@CacheEvict(value = "ConfigurationsSorts", allEntries = true), //
			@CacheEvict(value = "ConfigurationsPage", allEntries = true), //
			@CacheEvict(value = "ConfigurationsHistory", allEntries = true), //
			@CacheEvict(value = "ConfigurationsRelatedHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Configuration model = modelService.findOneEntityByUuid(uuid, Configuration.class);
			modelService.deleteByEntity(model, Configuration.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
