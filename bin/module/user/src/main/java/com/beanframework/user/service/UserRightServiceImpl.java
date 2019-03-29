package com.beanframework.user.service;

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

import com.beanframework.common.context.FetchContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.specification.UserRightSpecification;

@Service
public class UserRightServiceImpl implements UserRightService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private FetchContext fetchContext;

	@Override
	public UserRight create() throws Exception {
		return modelService.create(UserRight.class);
	}

	@Override
	public UserRight findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties();

		fetchContext.addFetchProperty(UserPermission.class, UserPermission.FIELDS);

		return modelService.findOneEntityByUuid(uuid, UserRight.class);
	}

	@Override
	public UserRight findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties();

		fetchContext.addFetchProperty(UserPermission.class, UserPermission.FIELDS);

		return modelService.findOneEntityByProperties(properties, UserRight.class);
	}

	@Override
	public List<UserRight> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, UserRight.class);
	}

	@Override
	public UserRight saveEntity(UserRight model) throws BusinessException {
		return (UserRight) modelService.saveEntity(model, UserRight.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			UserRight model = modelService.findOneEntityByUuid(uuid, UserRight.class);
			modelService.deleteByEntity(model, UserRight.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<UserRight> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		fetchContext.clearFetchProperties();
		return modelService.findEntityPage(UserRightSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), UserRight.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(UserRight.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), UserRight.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), UserRight.class);
	}
}
