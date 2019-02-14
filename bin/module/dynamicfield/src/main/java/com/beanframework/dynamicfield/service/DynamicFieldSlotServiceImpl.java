package com.beanframework.dynamicfield.service;

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
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

@Service
public class DynamicFieldSlotServiceImpl implements DynamicFieldSlotService {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldSlot create() throws Exception {
		return modelService.create(DynamicFieldSlot.class);
	}

	@Cacheable(value = "DynamicFieldSlotOne", key = "#uuid")
	@Override
	public DynamicFieldSlot findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, DynamicFieldSlot.class);
	}

	@Cacheable(value = "DynamicFieldSlotOneProperties", key = "#properties")
	@Override
	public DynamicFieldSlot findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, DynamicFieldSlot.class);
	}

	@Cacheable(value = "DynamicFieldSlotsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<DynamicFieldSlot> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, DynamicFieldSlot.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldSlotOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "DynamicFieldSlotOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldSlotsSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldSlotsPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldSlotsHistory", allEntries = true) }) //
	@Override
	public DynamicFieldSlot saveEntity(DynamicFieldSlot model) throws BusinessException {
		return (DynamicFieldSlot) modelService.saveEntity(model, DynamicFieldSlot.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldSlotOne", key = "#uuid"), //
			@CacheEvict(value = "DynamicFieldSlotOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldSlotsSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldSlotsPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldSlotsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			DynamicFieldSlot model = modelService.findOneEntityByUuid(uuid, true, DynamicFieldSlot.class);
			modelService.deleteByEntity(model, DynamicFieldSlot.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "DynamicFieldSlotsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<DynamicFieldSlot> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, DynamicFieldSlot.class);
	}

	@Cacheable(value = "DynamicFieldSlotsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(DynamicFieldSlot.class);
	}

	@Cacheable(value = "DynamicFieldSlotsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicFieldSlot.class);

	}

	@Cacheable(value = "DynamicFieldSlotsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicFieldSlot.class);
	}
}
