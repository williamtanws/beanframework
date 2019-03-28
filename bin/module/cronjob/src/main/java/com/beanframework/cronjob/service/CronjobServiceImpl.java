package com.beanframework.cronjob.service;

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
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.context.FetchContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;
import com.beanframework.cronjob.repository.CronjobRepository;
import com.beanframework.cronjob.specification.CronjobSpecification;

@Service
public class CronjobServiceImpl implements CronjobService {

	@Autowired
	private CronjobRepository cronjobRepository;

	@Autowired
	private ModelService modelService;

	@Autowired
	private FetchContext fetchContext;

	@Override
	public Cronjob create() throws Exception {
		return modelService.create(Cronjob.class);
	}

	@Override
	public Cronjob findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties(Cronjob.class);
		fetchContext.addFetchProperty(Cronjob.class, Cronjob.CRONJOB_DATAS);

		return modelService.findOneEntityByUuid(uuid, Cronjob.class);
	}

	@Override
	public Cronjob findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties(Cronjob.class);
		fetchContext.addFetchProperty(Cronjob.class, Cronjob.CRONJOB_DATAS);

		return modelService.findOneEntityByProperties(properties, Cronjob.class);
	}

	@Override
	public List<Cronjob> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Cronjob.class);
	}

	@Override
	public Cronjob saveEntity(Cronjob model) throws BusinessException {
		return (Cronjob) modelService.saveEntity(model, Cronjob.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Cronjob model = modelService.findOneEntityByUuid(uuid, Cronjob.class);
			modelService.deleteByEntity(model, Cronjob.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<Cronjob> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		fetchContext.clearFetchProperties(Cronjob.class);
		return modelService.findEntityPage(CronjobSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Cronjob.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Cronjob.class);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Cronjob> findEntityStartupJobIsFalseWithQueueJob() {
		return cronjobRepository.findStartupJobIsFalseWithQueueJob();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Cronjob.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Cronjob.class);
	}

	@Override
	public CronjobData findOneEntityCronjobDataByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, CronjobData.class);
	}
}
