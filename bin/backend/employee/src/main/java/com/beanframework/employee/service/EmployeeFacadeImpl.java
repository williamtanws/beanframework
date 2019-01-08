package com.beanframework.employee.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.converter.EntityEmployeeProfileConverter;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.service.AuditorFacade;

@Component
public class EmployeeFacadeImpl implements EmployeeFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Autowired
	private AuditorFacade auditorFacade;
	
	@Autowired
	private EntityEmployeeProfileConverter entityEmployeeProfileConverter;

	@Override
	public Employee saveProfile(Employee employee, MultipartFile picture) throws BusinessException {

		try {
			if (picture != null && picture.isEmpty() == false) {
				String mimetype = picture.getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == false) {
					throw new Exception(localeMessageService.getMessage(EmployeeConstants.Locale.PICTURE_WRONGFORMAT));
				}
			}
			employee = entityEmployeeProfileConverter.convert(employee);
			employee = (Employee) modelService.saveEntity(employee, Employee.class);
			updatePrincipal(employee);
			employeeService.saveProfilePicture(employee, picture);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

		return employee;
	}

	@Override
	public void updatePrincipal(Employee employee) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employeePrincipal = (Employee) auth.getPrincipal();
		employeePrincipal.setId(employee.getId());
		employeePrincipal.setName(employee.getName());
		employeePrincipal.setPassword(employee.getPassword());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(employeePrincipal, employeePrincipal.getPassword(), employeePrincipal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
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
	public Employee getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.getPrincipal() instanceof Employee) {

			Employee employee = (Employee) auth.getPrincipal();
			return employee;
		} else {
			return null;
		}
	}

	@Override
	public Employee findDtoAuthenticate(String id, String password) throws Exception {
		Employee employee = employeeService.findDtoAuthenticate(id, password);

		if (employee == null) {
			throw new BadCredentialsException("Bad Credentials");
		}
		if (employee.getEnabled() == false) {
			throw new DisabledException("Account Disabled");
		}

		if (employee.getAccountNonExpired() == false) {
			throw new AccountExpiredException("Account Expired");
		}

		if (employee.getAccountNonLocked() == false) {
			throw new LockedException("Account Locked");
		}

		if (employee.getCredentialsNonExpired() == false) {
			throw new CredentialsExpiredException("Passwrd Expired");
		}
		return employee;
	}

	@Override
	public void deleteEmployeeProfilePictureByUuid(UUID uuid) {
		employeeService.deleteEmployeeProfilePictureByUuid(uuid);
	}

	@Override
	public void deleteAllEmployeeProfilePicture() {
		employeeService.deleteAllEmployeeProfilePicture();
	}

	@Override
	public Page<Employee> findPage(Specification<Employee> specification, PageRequest pageRequest) throws Exception {
		return modelService.findDtoPage(specification, pageRequest, Employee.class);
	}

	@Override
	public Employee findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, Employee.class);
	}

	@Override
	public Employee findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, Employee.class);
	}

	@Override
	public Employee create() throws Exception {
		return modelService.create(Employee.class);
	}

	@Override
	public Employee createDto(Employee model) throws BusinessException {
		Employee employee = (Employee) modelService.saveDto(model, Employee.class);
		auditorFacade.save(employee);
		return employee;
	}

	@Override
	public Employee updateDto(Employee model) throws BusinessException {
		Employee employee = (Employee) modelService.saveDto(model, Employee.class);
		auditorFacade.save(employee);
		return employee;
	}

	@Override
	public Employee saveEntity(Employee model) throws BusinessException {
		Employee employee = (Employee) modelService.saveEntity(model, Employee.class);
		auditorFacade.save(employee);
		return employee;
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Employee.class);
	}
	
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, User.class);
		
		return revisions;
	}
	
	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(UserField.USER).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserField.class);
		
		return revisions;
	}

}
