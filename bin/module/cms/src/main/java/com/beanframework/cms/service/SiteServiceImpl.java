package com.beanframework.cms.service;

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

import com.beanframework.cms.domain.Site;
import com.beanframework.cms.specification.SiteSpecification;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;

@Service
public class SiteServiceImpl implements SiteService {

	@Autowired
	private ModelService modelService;

	@Override
	public Site findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Site.class);
	}

	@Override
	public Site findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Site.class);
	}

	@Override
	public List<Site> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Site.class);
	}

	@Override
	public Site saveEntity(Site model) throws BusinessException {
		return (Site) modelService.saveEntity(model, Site.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Site model = modelService.findOneEntityByUuid(uuid, Site.class);
			modelService.deleteByEntity(model, Site.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<Site> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(SiteSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Site.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Site.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		List<Object[]> histories = modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Site.class);
		return histories;
	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Site.class);
	}
}
