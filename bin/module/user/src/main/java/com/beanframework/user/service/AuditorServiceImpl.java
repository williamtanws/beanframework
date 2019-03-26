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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
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
	public Auditor create() throws Exception {
		return modelService.create(Auditor.class);
	}

	@Override
	public Auditor findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Auditor.class);
	}

	@Override
	public Auditor findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Auditor.class);
	}

	@Override
	public List<Auditor> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Auditor.class);
	}

	@Override
	public Auditor saveEntity(User model) throws BusinessException {
		try {
			Auditor auditor = modelService.findOneEntityByUuid(model.getUuid(), Auditor.class);
			if (auditor == null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Auditor.ID, model.getId());
				auditor = modelService.findOneEntityByProperties(properties, Auditor.class);
			}

			if (auditor == null) {
				auditor = modelService.create(Auditor.class);
				auditor.setUuid(model.getUuid());
				auditor.setId(model.getId());
				auditor.setName(model.getName());
			} else {
				Date lastModifiedDate = new Date();
				if (StringUtils.equals(model.getId(), auditor.getId()) == false) {
					auditor.setId(model.getId());
					auditor.setLastModifiedDate(lastModifiedDate);
				}

				if (StringUtils.equals(model.getName(), auditor.getName()) == false) {
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

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Auditor model = modelService.findOneEntityByUuid(uuid, Auditor.class);
			modelService.deleteByEntity(model, Auditor.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public <T> Page<Auditor> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), Auditor.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Auditor.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Auditor.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Auditor.class);
	}
}
