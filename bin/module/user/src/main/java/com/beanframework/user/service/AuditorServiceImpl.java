package com.beanframework.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;

@Service
public class AuditorServiceImpl implements AuditorService {

	@Autowired
	private ModelService modelService;

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Auditor.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.countHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Auditor.class);
	}

	@Override
	public Auditor saveEntityByUser(User model) throws BusinessException {
		try {
			Auditor auditor = modelService.findByUuid(model.getUuid(), Auditor.class);
			if (auditor == null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Auditor.ID, model.getId());
				auditor = modelService.findByProperties(properties, Auditor.class);
			}

			if (auditor == null) {
				auditor = modelService.create(Auditor.class);
				auditor.setUuid(model.getUuid());
				auditor.setId(model.getId());
				auditor.setName(model.getName());
			} else {
				Date lastModifiedDate = new Date();
				if (StringUtils.equals(model.getId(), auditor.getId()) == Boolean.FALSE) {
					auditor.setId(model.getId());
					auditor.setLastModifiedDate(lastModifiedDate);
				}

				if (StringUtils.equals(model.getName(), auditor.getName()) == Boolean.FALSE) {
					auditor.setName(model.getName());
					auditor.setLastModifiedDate(lastModifiedDate);
				}
			}

			modelService.saveEntity(auditor, Auditor.class);

			return auditor;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
