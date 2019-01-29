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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.domain.Admin;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.service.AuditorService;
import com.beanframework.user.utils.PasswordUtils;

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

	@Override
	public Admin create() throws Exception {
		return modelService.create(Admin.class);
	}

	@Cacheable(value = "AdminOne", key = "#uuid")
	@Override
	public Admin findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, Admin.class);
	}

	@Cacheable(value = "AdminOneProperties", key = "#properties")
	@Override
	public Admin findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Admin.class);
	}

	@Cacheable(value = "AdminsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Admin> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Admin.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "AdminOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "AdminOneProperties", allEntries = true), //
			@CacheEvict(value = "AdminsSorts", allEntries = true), //
			@CacheEvict(value = "AdminsPage", allEntries = true), //
			@CacheEvict(value = "AdminsHistory", allEntries = true) }) //
	@Override
	public Admin saveEntity(Admin model) throws BusinessException {
		model = (Admin) modelService.saveEntity(model, Admin.class);
		auditorService.saveEntity(model);
		return model;
	}

	@Caching(evict = { //
			@CacheEvict(value = "AdminOne", key = "#uuid"), //
			@CacheEvict(value = "AdminOneProperties", allEntries = true), //
			@CacheEvict(value = "AdminsSorts", allEntries = true), //
			@CacheEvict(value = "AdminsPage", allEntries = true), //
			@CacheEvict(value = "AdminsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Admin model = modelService.findOneEntityByUuid(uuid, true, Admin.class);
			modelService.deleteByEntity(model, Admin.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "AdminsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Admin> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Admin.class);
	}

	@Cacheable(value = "AdminsPage", key = "'count'")
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
		Admin entity = modelService.findOneEntityByProperties(properties, true, Admin.class);

		if (entity == null) {
			if (StringUtils.compare(password, defaultAdminPassword) != 0) {
				throw new BadCredentialsException("Bad Credentials");
			} else {
				entity = modelService.create(Admin.class);
				entity.setId(defaultAdminId);
			}
		} else {
			if (PasswordUtils.isMatch(password, entity.getPassword()) == false) {
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
	
	@Cacheable(value = "AdminsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());
		
		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Admin.class);

	}

	@Cacheable(value = "AdminsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Admin.class);
	}

}
