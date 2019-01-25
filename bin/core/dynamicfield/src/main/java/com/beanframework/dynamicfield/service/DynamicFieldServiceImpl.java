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
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;

@Service
public class DynamicFieldServiceImpl implements DynamicFieldService {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField create() throws Exception {
		return modelService.create(DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldOne", key = "#uuid")
	@Override
	public DynamicField findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldOneProperties", key = "#properties")
	@Override
	public DynamicField findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<DynamicField> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, DynamicField.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "DynamicFieldOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsHistory", allEntries = true) }) //
	@Override
	public DynamicField saveEntity(DynamicField model) throws BusinessException {
		return (DynamicField) modelService.saveEntity(model, DynamicField.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldOne", key = "#uuid"), //
			@CacheEvict(value = "DynamicFieldOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			DynamicField model = modelService.findOneEntityByUuid(uuid, true, DynamicField.class);
			modelService.deleteByEntity(model, DynamicField.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "DynamicFieldsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<DynamicField> findEntityPage(DataTableRequest<T> dataTableRequest) throws Exception {
		return modelService.findEntityPage(dataTableRequest.getSpecification(), dataTableRequest.getPageable(), false, DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicField.class);

	}

	@Cacheable(value = "DynamicFieldsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicField.class);
	}
}
