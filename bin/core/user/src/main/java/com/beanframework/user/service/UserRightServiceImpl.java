package com.beanframework.user.service;

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
import com.beanframework.user.domain.UserRight;

@Service
public class UserRightServiceImpl implements UserRightService {

	@Autowired
	private ModelService modelService;

	@Override
	public UserRight create() throws Exception {
		return modelService.create(UserRight.class);
	}

	@Cacheable(value = "UserRightOne", key = "#uuid")
	@Override
	public UserRight findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, UserRight.class);
	}

	@Cacheable(value = "UserRightOneProperties", key = "#properties")
	@Override
	public UserRight findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, UserRight.class);
	}

	@Cacheable(value = "UserRightsSorts", key = "#sorts")
	@Override
	public List<UserRight> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, UserRight.class);
	}

	@Cacheable(value = "UserRightsPage", key = "{#query}")
	@Override
	public <T> Page<UserRight> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, UserRight.class);
	}

	@Cacheable(value = "UserRightsHistory", key = "{#uuid, #firstResult, #maxResults}")
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, UserRight.class);
	}

	@Cacheable(value = "UserRightsRelatedHistory", key = "{#relatedEntity, #uuid, #firstResult, #maxResults}")
	@Override
	public List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(relatedEntity).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, UserRight.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserRightOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "UserRightOneProperties", allEntries = true), //
			@CacheEvict(value = "UserRightsSorts", allEntries = true), //
			@CacheEvict(value = "UserRightsPage", allEntries = true), //
			@CacheEvict(value = "UserRightsHistory", allEntries = true), //
			@CacheEvict(value = "UserRightsRelatedHistory", allEntries = true) }) //
	@Override
	public UserRight saveEntity(UserRight model) throws BusinessException {
		return (UserRight) modelService.saveEntity(model, UserRight.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserRightOne", key = "#uuid"), //
			@CacheEvict(value = "UserRightOneProperties", allEntries = true), //
			@CacheEvict(value = "UserRightsSorts", allEntries = true), //
			@CacheEvict(value = "UserRightsPage", allEntries = true), //
			@CacheEvict(value = "UserRightsHistory", allEntries = true), //
			@CacheEvict(value = "UserRightsRelatedHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			UserRight model = modelService.findOneEntityByUuid(uuid, UserRight.class);
			modelService.deleteByEntity(model, UserRight.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
