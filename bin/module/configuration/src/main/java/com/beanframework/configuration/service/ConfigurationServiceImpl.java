package com.beanframework.configuration.service;

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
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.specification.ConfigurationSpecification;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	private ModelService modelService;

	@Override
	public Configuration create() throws Exception {
		return modelService.create(Configuration.class);
	}

	@Override
	public Configuration findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Configuration.class);
	}

	@Override
	public Configuration findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Configuration.class);
	}

	@Override
	public List<Configuration> findAllEntity() throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, null, null, null, Configuration.class);
	}

	@Override
	public List<Configuration> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Configuration.class);
	}

	@Override
	public Configuration saveEntity(Configuration model) throws BusinessException {
		return (Configuration) modelService.saveEntity(model, Configuration.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Configuration model = modelService.findOneEntityByUuid(uuid, Configuration.class);
			modelService.deleteByEntity(model, Configuration.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<Configuration> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(ConfigurationSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Configuration.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Configuration.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Configuration.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Configuration.class);
	}
}
