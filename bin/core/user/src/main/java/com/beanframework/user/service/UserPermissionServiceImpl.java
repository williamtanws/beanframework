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
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermission create() throws Exception {
		return modelService.create(UserPermission.class);
	}

	@Cacheable(value = "UserPermissionOne", key = "#uuid")
	@Override
	public UserPermission findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, UserPermission.class);
	}

	@Cacheable(value = "UserPermissionOneProperties", key = "#properties")
	@Override
	public UserPermission findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true,UserPermission.class);
	}

	@Cacheable(value = "UserPermissionsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<UserPermission> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, UserPermission.class);
	}

	@Cacheable(value = "UserPermissionsPage", key = "'query:'+#query+',pageable'+#pageable")
	@Override
	public <T> Page<UserPermission> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, false, UserPermission.class);
	}

	@Cacheable(value = "UserPermissionsHistory", key = "'uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, UserPermission.class);
	}

	@Cacheable(value = "UserPermissionsRelatedHistory", key = "'relatedEntity:'+#relatedEntity+',uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(relatedEntity).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, UserPermissionField.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserPermissionOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "UserPermissionOneProperties", allEntries = true), //
			@CacheEvict(value = "UserPermissionsSorts", allEntries = true), //
			@CacheEvict(value = "UserPermissionsPage", allEntries = true), //
			@CacheEvict(value = "UserPermissionsHistory", allEntries = true), //
			@CacheEvict(value = "UserPermissionsRelatedHistory", allEntries = true) }) //
	@Override
	public UserPermission saveEntity(UserPermission model) throws BusinessException {
		return (UserPermission) modelService.saveEntity(model, UserPermission.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserPermissionOne", key = "#uuid"), //
			@CacheEvict(value = "UserPermissionOneProperties", allEntries = true), //
			@CacheEvict(value = "UserPermissionsSorts", allEntries = true), //
			@CacheEvict(value = "UserPermissionsPage", allEntries = true), //
			@CacheEvict(value = "UserPermissionsHistory", allEntries = true), //
			@CacheEvict(value = "UserPermissionsRelatedHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			UserPermission model = modelService.findOneEntityByUuid(uuid, true, UserPermission.class);
			modelService.deleteByEntity(model, UserPermission.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
