package com.beanframework.employee.service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.domain.EmployeeSession;
import com.beanframework.employee.validator.DeleteEmployeeValidator;
import com.beanframework.employee.validator.SaveEmployeeProfileValidator;
import com.beanframework.employee.validator.SaveEmployeeValidator;

@Component
public class EmployeeFacadeImpl implements EmployeeFacade {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SaveEmployeeValidator saveEmployeeValidator;

	@Autowired
	private SaveEmployeeProfileValidator saveEmployeeProfileValidator;

	@Autowired
	private DeleteEmployeeValidator deleteEmployeeValidator;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Override
	public Employee create() {
		return employeeService.create();
	}

	@Override
	public Employee initDefaults(Employee employee) {
		return employeeService.initDefaults(employee);
	}

	@Override
	public Employee save(Employee employee, Errors bindingResult) {
		saveEmployeeValidator.validate(employee, bindingResult);

		if (bindingResult.hasErrors()) {
			return employee;
		}

		return employeeService.save(employee);
	}

	@Override
	public Employee saveProfile(Employee employee, MultipartFile picture, Errors bindingResult) {
		saveEmployeeProfileValidator.validate(employee, bindingResult);
		saveEmployeeProfileValidator.validate(picture, bindingResult);

		if (bindingResult.hasErrors()) {
			return employee;
		}

		try {
			return employeeService.saveProfile(employee, picture);
		} catch (IOException e) {
			bindingResult.reject("employee", e.toString());
			return employee;
		}
	}

	@Override
	public Employee updatePrincipal(Employee employee) {
		return employeeService.updatePrincipal(employee);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteEmployeeValidator.validate(uuid, bindingResult);

		if (bindingResult.hasErrors() == false) {
			employeeService.delete(uuid);
		}
	}
	
	@Override
	public void delete(String id, Errors bindingResult) {
		deleteEmployeeValidator.validate(id, bindingResult);

		if (bindingResult.hasErrors() == false) {
			employeeService.delete(id);
		}
	}

	@Override
	public void deleteAll() {
		employeeService.deleteAll();
	}

	@Override
	public Employee findByUuid(UUID uuid) {
		return employeeService.findByUuid(uuid);
	}

	@Override
	public Employee findById(String id) {
		return employeeService.findById(id);
	}

	@Override
	public Set<EmployeeSession> findAllSessions() {
		final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		Set<EmployeeSession> sessions = new LinkedHashSet<EmployeeSession>();

		for (final Object principal : allPrincipals) {
			if (principal instanceof Employee) {
				final Employee principalEmployee = (Employee) principal;
				if (principalEmployee.getUuid() != null) {
					List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principalEmployee,
							false);
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
				List<SessionInformation> session = sessionRegistry
						.getAllSessions(employeeSession.getPrincipalEmployee(), false);
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
			List<SessionInformation> session = sessionRegistry.getAllSessions(employeeSession.getPrincipalEmployee(),
					false);
			for (SessionInformation sessionInformation : session) {
				sessionInformation.expireNow();
			}
		}
	}

	@Override
	public Page<Employee> page(Employee employee, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return employeeService.page(employee, pageRequest);
	}

	@Override
	public Employee getCurrentEmployee() {
		return employeeService.getCurrentEmployee();
	}

	@Override
	public Employee authenticate(String id, String password) {
		Employee employee = employeeService.authenticate(id, password);

		if (employee == null) {
			throw new BadCredentialsException("Bad Credentials");
		}
		if (employee.isEnabled() == false) {
			throw new DisabledException("Account Disabled");
		}

		if (employee.isAccountNonExpired() == false) {
			throw new AccountExpiredException("Account Expired");
		}

		if (employee.isAccountNonLocked() == false) {
			throw new LockedException("Account Locked");
		}

		if (employee.isCredentialsNonExpired() == false) {
			throw new CredentialsExpiredException("Passwrd Expired");
		}
		return employee;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public SaveEmployeeValidator getSaveEmployeeValidator() {
		return saveEmployeeValidator;
	}

	public void setSaveEmployeeValidator(SaveEmployeeValidator saveEmployeeValidator) {
		this.saveEmployeeValidator = saveEmployeeValidator;
	}

	public SaveEmployeeProfileValidator getSaveCurrentEmployeeValidator() {
		return saveEmployeeProfileValidator;
	}

	public void setSaveCurrentEmployeeValidator(SaveEmployeeProfileValidator saveCurrentEmployeeValidator) {
		this.saveEmployeeProfileValidator = saveCurrentEmployeeValidator;
	}

}
