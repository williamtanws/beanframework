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
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

@Service
public class DynamicFieldSlotServiceImpl implements DynamicFieldSlotService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private FetchContext fetchContext;

	@Override
	public DynamicFieldSlot create() throws Exception {
		return modelService.create(DynamicFieldSlot.class);
	}

	@Override
	public DynamicFieldSlot findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties(DynamicFieldSlot.class);

		fetchContext.addFetchProperty(DynamicFieldSlot.class, DynamicFieldSlot.DYNAMIC_FIELD);

		return modelService.findOneEntityByUuid(uuid, DynamicFieldSlot.class);
	}

	@Override
	public DynamicFieldSlot findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties(DynamicFieldSlot.class);

		fetchContext.addFetchProperty(DynamicFieldSlot.class, DynamicFieldSlot.DYNAMIC_FIELD);

		return modelService.findOneEntityByProperties(properties, DynamicFieldSlot.class);
	}

	@Override
	public List<DynamicFieldSlot> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, DynamicFieldSlot.class);
	}

	@Override
	public DynamicFieldSlot saveEntity(DynamicFieldSlot model) throws BusinessException {
		return (DynamicFieldSlot) modelService.saveEntity(model, DynamicFieldSlot.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			DynamicFieldSlot model = modelService.findOneEntityByUuid(uuid, DynamicFieldSlot.class);
			modelService.deleteByEntity(model, DynamicFieldSlot.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public <T> Page<DynamicFieldSlot> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), DynamicFieldSlot.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(DynamicFieldSlot.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicFieldSlot.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicFieldSlot.class);
	}
}
