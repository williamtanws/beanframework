package com.beanframework.cronjob.service;

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
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.repository.CronjobRepository;

@Service
public class CronjobServiceImpl implements CronjobService {

	@Autowired
	private CronjobRepository cronjobRepository;

	@Autowired
	private ModelService modelService;

	@Override
	public Cronjob create() throws Exception {
		return modelService.create(Cronjob.class);
	}

	@Cacheable(value = "CronjobOne", key = "#uuid")
	@Override
	public Cronjob findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, Cronjob.class);
	}

	@Cacheable(value = "CronjobOneProperties", key = "#properties")
	@Override
	public Cronjob findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Cronjob.class);
	}

	@Cacheable(value = "CronjobsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Cronjob> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Cronjob.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CronjobOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "CronjobOneProperties", allEntries = true), //
			@CacheEvict(value = "CronjobsSorts", allEntries = true), //
			@CacheEvict(value = "CronjobsPage", allEntries = true), //
			@CacheEvict(value = "CronjobsHistory", allEntries = true) }) //
	@Override
	public Cronjob saveEntity(Cronjob model) throws BusinessException {
		return (Cronjob) modelService.saveEntity(model, Cronjob.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CronjobOne", key = "#uuid"), //
			@CacheEvict(value = "CronjobOneProperties", allEntries = true), //
			@CacheEvict(value = "CronjobsSorts", allEntries = true), //
			@CacheEvict(value = "CronjobsPage", allEntries = true), //
			@CacheEvict(value = "CronjobsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Cronjob model = modelService.findOneEntityByUuid(uuid, true, Cronjob.class);
			modelService.deleteByEntity(model, Cronjob.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "CronjobsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Cronjob> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Cronjob.class);
	}

	@Cacheable(value = "CronjobsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Cronjob.class);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Cronjob> findEntityStartupJobIsFalseWithQueueJob() {
		return cronjobRepository.findStartupJobIsFalseWithQueueJob();
	}

	@Cacheable(value = "CronjobsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Cronjob.class);

	}

	@Cacheable(value = "CronjobsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Cronjob.class);
	}
}
