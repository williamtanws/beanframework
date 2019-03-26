package com.beanframework.dynamicfield.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.context.FetchContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Service
public class DynamicFieldTemplateServiceImpl implements DynamicFieldTemplateService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private FetchContext fetchContext;

	@Override
	public DynamicFieldTemplate create() throws Exception {
		return modelService.create(DynamicFieldTemplate.class);
	}

	@Override
	public DynamicFieldTemplate findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties(DynamicFieldTemplate.class);

		fetchContext.addFetchProperty(DynamicFieldTemplate.class, DynamicFieldTemplate.DYNAMIC_FIELD_SLOTS);

		return modelService.findOneEntityByUuid(uuid, DynamicFieldTemplate.class);
	}

	@Override
	public DynamicFieldTemplate findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties(DynamicFieldTemplate.class);

		fetchContext.addFetchProperty(DynamicFieldTemplate.class, DynamicFieldTemplate.DYNAMIC_FIELD_SLOTS);

		return modelService.findOneEntityByProperties(properties, DynamicFieldTemplate.class);
	}

	@Override
	public List<DynamicFieldTemplate> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, DynamicFieldTemplate.class);
	}

	@Override
	public DynamicFieldTemplate saveEntity(DynamicFieldTemplate model) throws BusinessException {
		return (DynamicFieldTemplate) modelService.saveEntity(model, DynamicFieldTemplate.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			DynamicFieldTemplate model = modelService.findOneEntityByUuid(uuid, DynamicFieldTemplate.class);
			modelService.deleteByEntity(model, DynamicFieldTemplate.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public <T> Page<DynamicFieldTemplate> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), DynamicFieldTemplate.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(DynamicFieldTemplate.class);
	}

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

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicFieldTemplate.class);
	}
}
