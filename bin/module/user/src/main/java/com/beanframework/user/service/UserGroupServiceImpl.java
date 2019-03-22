package com.beanframework.user.service;

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
import com.beanframework.user.domain.UserGroup;

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

	@Cacheable(value = "UserGroupsSorts", key = "'sorts:'+#sorts")
	@Override
	public List<UserGroup> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, UserGroup.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserGroupOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "UserGroupOneProperties", allEntries = true), //
			@CacheEvict(value = "UserGroupsSorts", allEntries = true), //
			@CacheEvict(value = "UserGroupsPage", allEntries = true), //
			@CacheEvict(value = "UserGroupsHistory", allEntries = true) }) //
	@Override
	public UserGroup saveEntity(UserGroup model) throws BusinessException {
		return (UserGroup) modelService.saveEntity(model, UserGroup.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserGroupOne", key = "#uuid"), //
			@CacheEvict(value = "UserGroupOneProperties", allEntries = true), //
			@CacheEvict(value = "UserGroupsSorts", allEntries = true), //
			@CacheEvict(value = "UserGroupsPage", allEntries = true), //
			@CacheEvict(value = "UserGroupsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			UserGroup model = modelService.findOneEntityByUuid(uuid, UserGroup.class);
			modelService.deleteByEntity(model, UserGroup.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "UserGroupsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<UserGroup> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), UserGroup.class);
	}

	@Cacheable(value = "UserGroupsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(UserGroup.class);
	}

	@Cacheable(value = "UserGroupsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), UserGroup.class);

	}

	@Cacheable(value = "UserGroupsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), UserGroup.class);
	}
}
