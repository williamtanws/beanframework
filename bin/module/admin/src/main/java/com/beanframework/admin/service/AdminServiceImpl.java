package com.beanframework.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.specification.AdminSpecification;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.service.AuditorService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AuditorService auditorService;

	@Value(AdminConstants.Admin.DEFAULT_ID)
	private String defaultAdminId;

	@Value(AdminConstants.Admin.DEFAULT_PASSWORD)
	private String defaultAdminPassword;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Admin create() throws Exception {
		Admin admin = modelService.create(Admin.class);
		return admin;
	}

	@Override
	public Admin findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Admin.class);
	}

	@Override
	public Admin findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Admin.class);
	}

	@Override
	public List<Admin> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Admin.class);
	}

	@Override
	public Admin saveEntity(Admin model) throws BusinessException {
		model = (Admin) modelService.saveEntity(model, Admin.class);
		auditorService.saveEntity(model);
		return model;
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Admin model = modelService.findOneEntityByUuid(uuid, Admin.class);
			modelService.deleteByEntity(model, Admin.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<Admin> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(AdminSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Admin.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Admin.class);
	}

	@Override
	public Admin findAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			return null;
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Admin.ID, id);
		Admin entity = modelService.findOneEntityByProperties(properties, Admin.class);

		if (entity == null) {
			if (StringUtils.compare(password, defaultAdminPassword) != 0) {
				throw new BadCredentialsException("Bad Credentials");
			} else {
				entity = modelService.create(Admin.class);
				entity.setId(defaultAdminId);
			}
		} else {
			if (passwordEncoder.matches(password, entity.getPassword()) == false) {
				throw new BadCredentialsException("Bad Credentials");
			}
		}

		if (entity.getEnabled() == false) {
			throw new DisabledException("Account Disabled");
		}

		if (entity.getAccountNonExpired() == false) {
			throw new AccountExpiredException("Account Expired");
		}

		if (entity.getAccountNonLocked() == false) {
			throw new LockedException("Account Locked");
		}

		if (entity.getCredentialsNonExpired() == false) {
			throw new CredentialsExpiredException("Password Expired");
		}

		return entity;
	}

	@Override
	public Admin getCurrentUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Admin admin = (Admin) auth.getPrincipal();
			return admin;
		} else {
			return null;
		}
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Admin.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Admin.class);
	}

}
