package com.beanframework.employee.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
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
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.specification.EmployeeSpecification;
import com.beanframework.user.service.AuditorService;
import com.beanframework.user.service.UserService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private AuditorService auditorService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EntityManager entityManager;

	@Override
	public Employee create() throws Exception {
		return modelService.create(Employee.class);
	}

	@Override
	public Employee findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Employee.class);
	}

	@Override
	public Employee findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Employee.class);
	}

	@Override
	public List<Employee> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Employee.class);
	}

	@Override
	public Employee saveEntity(Employee model) throws BusinessException {
		model = (Employee) modelService.saveEntity(model, Employee.class);
		auditorService.saveEntity(model);
		return model;
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Employee model = modelService.findOneEntityByUuid(uuid, Employee.class);
			modelService.deleteByEntity(model, Employee.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<Employee> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(EmployeeSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Employee.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Employee.class);
	}

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
		userService.deleteProfilePictureByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Employee findAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Bad Credentials");
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Employee.ID, id);

		Employee entity = findOneEntityByProperties(properties);

		if (entity == null) {
			throw new BadCredentialsException("Bad Credentials");
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
	public Employee getCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Employee principal = (Employee) auth.getPrincipal();
			return findOneEntityByUuid(principal.getUuid());
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

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Employee.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Employee.class);
	}

	@Override
	public int countByUserGroups(List<UUID> userGroupsUuid) {
		if (userGroupsUuid == null || userGroupsUuid.size() == 0) {
			return 0;
		}

		Query query = entityManager.createQuery("SELECT COUNT(DISTINCT o) FROM Employee o LEFT JOIN o.userGroups u WHERE (u.uuid IN (?1))");
		query.setParameter(1, userGroupsUuid);

		Long count = (Long) query.getSingleResult();
		return count.intValue();
	}
}
