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
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.specification.UserGroupSpecification;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private FetchContext fetchContext;

	@Override
	public UserGroup create() throws Exception {
		return modelService.create(UserGroup.class);
	}

	@Override
	public UserGroup findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties(UserGroup.class);
		fetchContext.clearFetchProperties(UserAuthority.class);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_GROUPS);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.FIELDS);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_AUTHORITIES);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_PERMISSION);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_RIGHT);
		return modelService.findOneEntityByUuid(uuid, UserGroup.class);
	}

	@Override
	public UserGroup findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties(UserGroup.class);
		fetchContext.clearFetchProperties(UserAuthority.class);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_GROUPS);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.FIELDS);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_AUTHORITIES);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_PERMISSION);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_RIGHT);
		return modelService.findOneEntityByProperties(properties, UserGroup.class);
	}

	@Override
	public List<UserGroup> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, UserGroup.class);
	}

	@Override
	public UserGroup saveEntity(UserGroup model) throws BusinessException {
		return (UserGroup) modelService.saveEntity(model, UserGroup.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			UserGroup model = modelService.findOneEntityByUuid(uuid, UserGroup.class);
			modelService.deleteByEntity(model, UserGroup.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<UserGroup> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(UserGroupSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), UserGroup.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(UserGroup.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), UserGroup.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), UserGroup.class);
	}
}
