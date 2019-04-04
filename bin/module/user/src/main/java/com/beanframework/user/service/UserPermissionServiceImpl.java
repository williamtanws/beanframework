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
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.specification.UserPermissionSpecification;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private FetchContext fetchContext;

	@Override
	public UserPermission create() throws Exception {
		return modelService.create(UserPermission.class);
	}

	@Override
	public UserPermission findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties();
		
		fetchContext.addFetchProperty(UserPermission.class, UserPermission.FIELDS);
		fetchContext.addFetchProperty(UserPermissionField.class, UserPermissionField.DYNAMIC_FIELD_SLOT);
		fetchContext.addFetchProperty(DynamicFieldSlot.class, DynamicFieldSlot.DYNAMIC_FIELD);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.LANGUAGE);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.ENUMERATIONS);
		
		return modelService.findOneEntityByUuid(uuid, UserPermission.class);
	}

	@Override
	public UserPermission findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties();

		fetchContext.addFetchProperty(UserPermission.class, UserPermission.FIELDS);
		fetchContext.addFetchProperty(UserPermissionField.class, UserPermissionField.DYNAMIC_FIELD_SLOT);
		fetchContext.addFetchProperty(DynamicFieldSlot.class, DynamicFieldSlot.DYNAMIC_FIELD);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.LANGUAGE);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.ENUMERATIONS);
		
		return modelService.findOneEntityByProperties(properties, UserPermission.class);
	}

	@Override
	public List<UserPermission> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, UserPermission.class);
	}

	@Override
	public UserPermission saveEntity(UserPermission model) throws BusinessException {
		return (UserPermission) modelService.saveEntity(model, UserPermission.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			UserPermission model = modelService.findOneEntityByUuid(uuid, UserPermission.class);
			modelService.deleteByEntity(model, UserPermission.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<UserPermission> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		fetchContext.clearFetchProperties();
		return modelService.findEntityPage(UserPermissionSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), UserPermission.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(UserPermission.class);

	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), UserPermission.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), UserPermission.class);
	}
}
