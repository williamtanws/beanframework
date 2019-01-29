package com.beanframework.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Hibernate;
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

import com.beanframework.cms.domain.Site;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;

@Service
public class SiteServiceImpl implements SiteService {

	@Autowired
	private ModelService modelService;

	@Override
	public Site create() throws Exception {
		return modelService.create(Site.class);
	}

	@Cacheable(value = "SiteOne", key = "#uuid")
	@Override
	public Site findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, Site.class);
	}

	@Cacheable(value = "SiteOneProperties", key = "#properties")
	@Override
	public Site findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Site.class);
	}

	@Cacheable(value = "SitesSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Site> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Site.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "SiteOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "SiteOneProperties", allEntries = true), //
			@CacheEvict(value = "SitesSorts", allEntries = true), //
			@CacheEvict(value = "SitesPage", allEntries = true), //
			@CacheEvict(value = "SitesHistory", allEntries = true) }) //
	@Override
	public Site saveEntity(Site model) throws BusinessException {
		return (Site) modelService.saveEntity(model, Site.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "SiteOne", key = "#uuid"), //
			@CacheEvict(value = "SiteOneProperties", allEntries = true), //
			@CacheEvict(value = "SitesSorts", allEntries = true), //
			@CacheEvict(value = "SitesPage", allEntries = true), //
			@CacheEvict(value = "SitesHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Site model = modelService.findOneEntityByUuid(uuid, true, Site.class);
			modelService.deleteByEntity(model, Site.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "SitesPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Site> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Site.class);
	}

	@Cacheable(value = "SitesPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Site.class);
	}

	@Cacheable(value = "SitesHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		List<Object[]> histories = modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Site.class);
		for (Object[] objects : histories) {
			Site site = (Site) objects[0];
			Hibernate.initialize(site.getLastModifiedBy());
			Hibernate.unproxy(site.getLastModifiedBy());
		}
		return histories;
	}

	@Cacheable(value = "SitesHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Site.class);
	}
}
