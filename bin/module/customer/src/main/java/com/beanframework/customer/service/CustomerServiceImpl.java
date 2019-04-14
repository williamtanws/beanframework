package com.beanframework.customer.service;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.context.FetchContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.specification.CustomerSpecification;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.AuditorService;
import com.beanframework.user.service.UserService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AuditorService auditorService;

	@Autowired
	private FetchContext fetchContext;

	@Autowired
	private UserService userService;

	@Override
	public Customer create() throws Exception {
		return modelService.create(Customer.class);
	}

	@Override
	public Customer findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties();

		fetchContext.addFetchProperty(Customer.class, Customer.USER_GROUPS);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_AUTHORITIES);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_GROUPS);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_PERMISSION);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_RIGHT);

		fetchContext.addFetchProperty(Customer.class, Customer.FIELDS);
		fetchContext.addFetchProperty(UserField.class, UserField.DYNAMIC_FIELD_SLOT);
		fetchContext.addFetchProperty(DynamicFieldSlot.class, DynamicFieldSlot.DYNAMIC_FIELD);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.LANGUAGE);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.ENUMERATIONS);

		return modelService.findOneEntityByUuid(uuid, Customer.class);
	}

	@Override
	public Customer findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties();

		fetchContext.addFetchProperty(Customer.class, Customer.USER_GROUPS);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_AUTHORITIES);
		fetchContext.addFetchProperty(UserGroup.class, UserGroup.USER_GROUPS);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_PERMISSION);
		fetchContext.addFetchProperty(UserAuthority.class, UserAuthority.USER_RIGHT);

		fetchContext.addFetchProperty(Customer.class, Customer.FIELDS);
		fetchContext.addFetchProperty(UserField.class, UserField.DYNAMIC_FIELD_SLOT);
		fetchContext.addFetchProperty(DynamicFieldSlot.class, DynamicFieldSlot.DYNAMIC_FIELD);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.LANGUAGE);
		fetchContext.addFetchProperty(DynamicField.class, DynamicField.ENUMERATIONS);

		return modelService.findOneEntityByProperties(properties, Customer.class);
	}

	@Override
	public List<Customer> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Customer.class);
	}

	@Override
	public Customer saveEntity(Customer model) throws BusinessException {
		model = (Customer) modelService.saveEntity(model, Customer.class);
		auditorService.saveEntity(model);
		return model;
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Customer model = modelService.findOneEntityByUuid(uuid, Customer.class);
			modelService.deleteByEntity(model, Customer.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<Customer> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		fetchContext.clearFetchProperties();
		return modelService.findEntityPage(CustomerSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Customer.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Customer.class);
	}

	@Override
	public void saveProfilePicture(Customer model, MultipartFile picture) throws IOException {
		userService.saveProfilePicture(model, picture);
	}

	@Override
	public void saveProfilePicture(Customer model, InputStream inputStream) throws IOException {
		userService.saveProfilePicture(model, inputStream);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Customer.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Customer.class);
	}

	@Override
	public Customer getCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Customer principal = (Customer) auth.getPrincipal();
			return findOneEntityByUuid(principal.getUuid());
		} else {
			return null;
		}
	}

	@Override
	public Customer updatePrincipal(Customer model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer principal = (Customer) auth.getPrincipal();
		principal.setId(model.getId());
		principal.setName(model.getName());
		principal.setPassword(model.getPassword());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return principal;
	}
}
