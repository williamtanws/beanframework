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
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroup create() throws Exception {
		return modelService.create(UserGroup.class);
	}

	@Cacheable(value = "UserGroupOne", key = "#uuid")
	@Override
	public UserGroup findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, UserGroup.class);
	}

	@Cacheable(value = "UserGroupOneProperties", key = "#properties")
	@Override
	public UserGroup findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, UserGroup.class);
	}

	@Cacheable(value = "UserGroupsSorts", key = "#sorts")
	@Override
	public List<UserGroup> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, UserGroup.class);
	}

	@Cacheable(value = "UserGroupsPage", key = "#query")
	@Override
	public <T> Page<UserGroup> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, UserGroup.class);
	}

	@Cacheable(value = "UserGroupsHistory", key = "'uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, UserGroup.class);
	}

	@Cacheable(value = "UserGroupsRelatedHistory", key = "'relatedEntity:'+#relatedEntity+',uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(relatedEntity).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, UserGroupField.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserGroupOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "UserGroupOneProperties", allEntries = true), //
			@CacheEvict(value = "UserGroupsSorts", allEntries = true), //
			@CacheEvict(value = "UserGroupsPage", allEntries = true), //
			@CacheEvict(value = "UserGroupsHistory", allEntries = true), //
			@CacheEvict(value = "UserGroupsRelatedHistory", allEntries = true) }) //
	@Override
	public UserGroup saveEntity(UserGroup model) throws BusinessException {
		return (UserGroup) modelService.saveEntity(model, UserGroup.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserGroupOne", key = "#uuid"), //
			@CacheEvict(value = "UserGroupOneProperties", allEntries = true), //
			@CacheEvict(value = "UserGroupsSorts", allEntries = true), //
			@CacheEvict(value = "UserGroupsPage", allEntries = true), //
			@CacheEvict(value = "UserGroupsHistory", allEntries = true), //
			@CacheEvict(value = "UserGroupsRelatedHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			UserGroup model = modelService.findOneEntityByUuid(uuid, UserGroup.class);
			modelService.deleteByEntity(model, UserGroup.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
