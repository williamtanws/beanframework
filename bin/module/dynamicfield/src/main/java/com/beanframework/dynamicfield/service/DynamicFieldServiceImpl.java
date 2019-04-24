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
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.specification.DynamicFieldSpecification;

@Service
public class DynamicFieldServiceImpl implements DynamicFieldService {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField create() throws Exception {
		return modelService.create(DynamicField.class);
	}

	@Override
	public DynamicField findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, DynamicField.class);
	}

	@Override
	public DynamicField findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, DynamicField.class);
	}

	@Override
	public List<DynamicField> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, DynamicField.class);
	}

	@Override
	public DynamicField saveEntity(DynamicField model) throws BusinessException {
		return (DynamicField) modelService.saveEntity(model, DynamicField.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			DynamicField model = modelService.findOneEntityByUuid(uuid, DynamicField.class);
			modelService.deleteByEntity(model, DynamicField.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<DynamicField> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(DynamicFieldSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), DynamicField.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(DynamicField.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicField.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicField.class);
	}
}
