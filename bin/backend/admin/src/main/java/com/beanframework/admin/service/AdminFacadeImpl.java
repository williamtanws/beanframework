package com.beanframework.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;

@Component
public class AdminFacadeImpl implements AdminFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AdminService adminService;

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
	public Admin findDtoAuthenticate(String id, String password) throws Exception {
		Admin admin = adminService.findDtoAuthenticate(id, password);

		if (admin == null) {
			throw new BadCredentialsException("Bad Credentials");
		}
		if (admin.getEnabled() == false) {
			throw new DisabledException("Account Disabled");
		}

		if (admin.getAccountNonExpired() == false) {
			throw new AccountExpiredException("Account Expired");
		}

		if (admin.getAccountNonLocked() == false) {
			throw new LockedException("Account Locked");
		}

		if (admin.getCredentialsNonExpired() == false) {
			throw new CredentialsExpiredException("Password Expired");
		}
		return admin;
	}

	@Override
	public Page<Admin> findDtoPage(Specification<Admin> findByCriteria, PageRequest pageable) throws Exception {
		return modelService.findDtoPage(findByCriteria, pageable, Admin.class);
	}

	@Override
	public Admin create() throws Exception {
		return modelService.create(Admin.class);
	}

	@Override
	public Admin findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, Admin.class);
	}

	@Override
	public Admin createDto(Admin model) throws BusinessException {
		return (Admin) modelService.saveDto(model, Admin.class);
	}

	@Override
	public Admin saveDto(Admin model) throws BusinessException {
		return (Admin) modelService.saveDto(model, Admin.class);

	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Admin.class);
	}

	@Override
	public Admin saveEntity(Admin model) throws BusinessException {
		return (Admin) modelService.saveEntity(model,Admin.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Admin.ID, id);
			Admin model = modelService.findOneEntityByProperties(properties, Admin.class);
			modelService.deleteByEntity(model, Admin.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Admin.class);
		
		return revisions;
	}
}
