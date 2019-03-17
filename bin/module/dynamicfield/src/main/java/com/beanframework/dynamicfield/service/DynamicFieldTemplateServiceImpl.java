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
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Service
public class DynamicFieldTemplateServiceImpl implements DynamicFieldTemplateService {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldTemplate create() throws Exception {
		return modelService.create(DynamicFieldTemplate.class);
	}

	@Cacheable(value = "DynamicFieldTemplateOne", key = "#uuid")
	@Override
	public DynamicFieldTemplate findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, DynamicFieldTemplate.class);
	}

	@Cacheable(value = "DynamicFieldTemplateOneProperties", key = "#properties")
	@Override
	public DynamicFieldTemplate findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, DynamicFieldTemplate.class);
	}

	@Cacheable(value = "DynamicFieldTemplatesSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<DynamicFieldTemplate> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, DynamicFieldTemplate.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldTemplateOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "DynamicFieldTemplateOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldTemplatesSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldTemplatesPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldTemplatesHistory", allEntries = true) }) //
	@Override
	public DynamicFieldTemplate saveEntity(DynamicFieldTemplate model) throws BusinessException {
		return (DynamicFieldTemplate) modelService.saveEntity(model, DynamicFieldTemplate.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldTemplateOne", key = "#uuid"), //
			@CacheEvict(value = "DynamicFieldTemplateOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldTemplatesSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldTemplatesPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldTemplatesHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			DynamicFieldTemplate model = modelService.findOneEntityByUuid(uuid, true, DynamicFieldTemplate.class);
			modelService.deleteByEntity(model, DynamicFieldTemplate.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "DynamicFieldTemplatesPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<DynamicFieldTemplate> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, DynamicFieldTemplate.class);
	}

	@Cacheable(value = "DynamicFieldTemplatesPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(DynamicFieldTemplate.class);
	}

	@Cacheable(value = "DynamicFieldTemplatesHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicFieldTemplate.class);

	}

	@Cacheable(value = "DynamicFieldTemplatesHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicFieldTemplate.class);
	}
}
