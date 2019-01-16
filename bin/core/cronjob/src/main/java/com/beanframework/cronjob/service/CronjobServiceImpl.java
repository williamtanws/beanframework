package com.beanframework.cronjob.service;

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
import org.springframework.transaction.annotation.Transactional;

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
		return modelService.findOneEntityByUuid(uuid, Cronjob.class);
	}

	@Cacheable(value = "CronjobOneProperties", key = "#properties")
	@Override
	public Cronjob findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Cronjob.class);
	}

	@Cacheable(value = "CronjobsSorts", key = "#sorts")
	@Override
	public List<Cronjob> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Cronjob.class);
	}

	@Cacheable(value = "CronjobsPage", key = "{#query}")
	@Override
	public <T> Page<Cronjob> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, Cronjob.class);
	}

	@Cacheable(value = "CronjobsHistory", key = "{#uuid, #firstResult, #maxResults}")
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, Cronjob.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CronjobOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "CronjobOneProperties", allEntries = true), //
			@CacheEvict(value = "CronjobsSorts", allEntries = true), //
			@CacheEvict(value = "CronjobsPage", allEntries = true), //
			@CacheEvict(value = "CronjobsHistory", allEntries = true), //
			@CacheEvict(value = "CronjobsRelatedHistory", allEntries = true) }) //
	@Override
	public Cronjob saveEntity(Cronjob model) throws BusinessException {
		return (Cronjob) modelService.saveEntity(model, Cronjob.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CronjobOne", key = "#uuid"), //
			@CacheEvict(value = "CronjobOneProperties", allEntries = true), //
			@CacheEvict(value = "CronjobsSorts", allEntries = true), //
			@CacheEvict(value = "CronjobsPage", allEntries = true), //
			@CacheEvict(value = "CronjobsHistory", allEntries = true), //
			@CacheEvict(value = "CronjobsRelatedHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Cronjob model = modelService.findOneEntityByUuid(uuid, Cronjob.class);
			modelService.deleteByEntity(model, Cronjob.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Cronjob> findEntityStartupJobIsFalseWithQueueJob() {
		return cronjobRepository.findStartupJobIsFalseWithQueueJob();
	}

}
