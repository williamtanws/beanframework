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
import com.beanframework.user.domain.UserPermission;

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
		return modelService.findOneEntityByProperties(properties, true, UserPermission.class);
	}

	@Cacheable(value = "UserPermissionsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<UserPermission> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, UserPermission.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserPermissionOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "UserPermissionOneProperties", allEntries = true), //
			@CacheEvict(value = "UserPermissionsSorts", allEntries = true), //
			@CacheEvict(value = "UserPermissionsPage", allEntries = true), //
			@CacheEvict(value = "UserPermissionsHistory", allEntries = true) }) //
	@Override
	public UserPermission saveEntity(UserPermission model) throws BusinessException {
		return (UserPermission) modelService.saveEntity(model, UserPermission.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserPermissionOne", key = "#uuid"), //
			@CacheEvict(value = "UserPermissionOneProperties", allEntries = true), //
			@CacheEvict(value = "UserPermissionsSorts", allEntries = true), //
			@CacheEvict(value = "UserPermissionsPage", allEntries = true), //
			@CacheEvict(value = "UserPermissionsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			UserPermission model = modelService.findOneEntityByUuid(uuid, true, UserPermission.class);
			modelService.deleteByEntity(model, UserPermission.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "UserPermissionsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<UserPermission> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, UserPermission.class);
	}

	@Cacheable(value = "UserPermissionsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(UserPermission.class);

	}

	@Cacheable(value = "UserPermissionsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), UserPermission.class);

	}

	@Cacheable(value = "UserPermissionsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), UserPermission.class);
	}
}
