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
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.service.AuditorService;

@Component
public class EmployeeFacadeImpl implements EmployeeFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private SessionRegistry sessionRegistry;
	@Autowired
	private AuditorService auditorService;

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
	public Page<Employee> findPage(Specification<Employee> specification, PageRequest pageRequest) throws Exception {
		return modelService.findEntityPage(specification, pageRequest, Employee.class);
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
	public Employee createDto(Employee model) throws BusinessException {
		Employee employee = (Employee) modelService.saveDto(model, Employee.class);
		auditorService.saveDto(employee);
		return employee;
	}

	@Override
	public Employee updateDto(Employee model) throws BusinessException {
		Employee employee = (Employee) modelService.saveDto(model, Employee.class);
		auditorService.saveDto(employee);
		return employee;
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, Employee.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
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
