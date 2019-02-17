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
		return modelService.findOneEntityByUuid(uuid, true, UserRight.class);
	}

	@Cacheable(value = "UserRightOneProperties", key = "#properties")
	@Override
	public UserRight findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, UserRight.class);
	}

	@Cacheable(value = "UserRightsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<UserRight> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, UserRight.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserRightOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "UserRightOneProperties", allEntries = true), //
			@CacheEvict(value = "UserRightsSorts", allEntries = true), //
			@CacheEvict(value = "UserRightsPage", allEntries = true), //
			@CacheEvict(value = "UserRightsHistory", allEntries = true) }) //
	@Override
	public UserRight saveEntity(UserRight model) throws BusinessException {
		return (UserRight) modelService.saveEntity(model, UserRight.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "UserRightOne", key = "#uuid"), //
			@CacheEvict(value = "UserRightOneProperties", allEntries = true), //
			@CacheEvict(value = "UserRightsSorts", allEntries = true), //
			@CacheEvict(value = "UserRightsPage", allEntries = true), //
			@CacheEvict(value = "UserRightsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			UserRight model = modelService.findOneEntityByUuid(uuid, true, UserRight.class);
			modelService.deleteByEntity(model, UserRight.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "UserRightsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<UserRight> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, UserRight.class);
	}

	@Cacheable(value = "UserRightsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(UserRight.class);
	}

	@Cacheable(value = "UserRightsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), UserRight.class);

	}

	@Cacheable(value = "UserRightsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), UserRight.class);
	}
}
