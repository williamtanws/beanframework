package com.beanframework.user.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.EmployeeSession;
import com.beanframework.user.domain.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void saveProfilePicture(Employee model, MultipartFile picture) throws IOException {
		userService.saveProfilePicture(model, picture);
	}

	@Override
	public void saveProfilePicture(Employee model, InputStream inputStream) throws IOException {
		userService.saveProfilePicture(model, inputStream);
	}

	@Override
	public void deleteEmployeeProfilePictureByUuid(UUID uuid) {
		userService.deleteProfilePictureFileByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Employee findAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Bad Credentials");
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Employee.ID, id);

		Employee entity = modelService.findOneByProperties(properties, Employee.class);

		if (entity == null) {
			throw new BadCredentialsException("Bad Credentials");
		} else {

			if (passwordEncoder.matches(password, entity.getPassword()) == Boolean.FALSE) {
				throw new BadCredentialsException("Bad Credentials");
			}
		}

		if (entity.getEnabled() == Boolean.FALSE) {
			throw new DisabledException("Account Disabled");
		}

		if (entity.getAccountNonExpired() == Boolean.FALSE) {
			throw new AccountExpiredException("Account Expired");
		}

		if (entity.getAccountNonLocked() == Boolean.FALSE) {
			throw new LockedException("Account Locked");
		}

		if (entity.getCredentialsNonExpired() == Boolean.FALSE) {
			throw new CredentialsExpiredException("Password Expired");
		}

		return entity;
	}

	@Override
	public Employee getCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Employee principal = (Employee) auth.getPrincipal();
			return modelService.findOneByUuid(principal.getUuid(), Employee.class);
		} else {
			return null;
		}
	}

	@Override
	public Employee updatePrincipal(Employee model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee principal = (Employee) auth.getPrincipal();
		principal.setId(model.getId());
		principal.setName(model.getName());
		principal.setPassword(model.getPassword());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return principal;
	}

	@Override
	public Set<EmployeeSession> findAllSessions() {
		final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		Set<EmployeeSession> sessions = new LinkedHashSet<EmployeeSession>();

		for (final Object principal : allPrincipals) {
			if (principal instanceof Employee) {
				final Employee principalEmployee = (Employee) principal;
				if (principalEmployee.getUuid() != null) {
					List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principalEmployee, false);
					sessions.add(new EmployeeSession(principalEmployee, sessionInformations));
				}
			}
		}
		return sessions;
	}

	@Override
	public void expireAllSessionsByUuid(UUID uuid) {
		Set<EmployeeSession> userList = findAllSessions();

		for (EmployeeSession employeeSession : userList) {
			if (employeeSession.getPrincipalEmployee().getUuid().equals(uuid)) {
				List<SessionInformation> session = sessionRegistry.getAllSessions(employeeSession.getPrincipalEmployee(), false);
				for (SessionInformation sessionInformation : session) {
					sessionInformation.expireNow();
				}
			}
		}
	}

	@Override
	public void expireAllSessions() {
		Set<EmployeeSession> userList = findAllSessions();

		for (EmployeeSession employeeSession : userList) {
			List<SessionInformation> session = sessionRegistry.getAllSessions(employeeSession.getPrincipalEmployee(), false);
			for (SessionInformation sessionInformation : session) {
				sessionInformation.expireNow();
			}
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

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Employee.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.countHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Employee.class);
	}
}
