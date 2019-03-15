package com.beanframework.configuration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
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
		return modelService.findOneEntityByUuid(uuid, true, Configuration.class);
	}

	@Cacheable(value = "ConfigurationOneProperties", key = "#properties")
	@Override
	public Configuration findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Configuration.class);
	}

	@Cacheable(value = "ConfigurationsAll")
	@Override
	public List<Configuration> findAllEntity() throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, null, null, null, true, Configuration.class);
	}

	@Cacheable(value = "ConfigurationsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Configuration> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Configuration.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "ConfigurationOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "ConfigurationOneProperties", allEntries = true), //
			@CacheEvict(value = "ConfigurationsAll", allEntries = true), //
			@CacheEvict(value = "ConfigurationsSorts", allEntries = true), //
			@CacheEvict(value = "ConfigurationsPage", allEntries = true), //
			@CacheEvict(value = "ConfigurationsHistory", allEntries = true) }) //
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
			@CacheEvict(value = "ConfigurationsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Configuration model = modelService.findOneEntityByUuid(uuid, true, Configuration.class);
			modelService.deleteByEntity(model, Configuration.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "ConfigurationsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Configuration> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Configuration.class);
	}

	@Cacheable(value = "ConfigurationsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Configuration.class);
	}

	@Cacheable(value = "ConfigurationsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Configuration.class);

	}

	@Cacheable(value = "ConfigurationsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Configuration.class);
	}
}
