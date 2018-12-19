package com.beanframework.employee.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.domain.Employee;

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
			
			modelService.saveEntity(employee);
			employeeService.saveProfilePicture(employee, picture);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
		return employee;
	}

	@Override
	public Employee updatePrincipal(Employee employee) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employeePrincipal = (Employee) auth.getPrincipal();
		employeePrincipal = dtoEmployeePrincipal(employee);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(employeePrincipal,
				employeePrincipal.getPassword(), employeePrincipal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return employeePrincipal;
	}
	
	private Employee dtoEmployeePrincipal(Employee source) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employeePrincipal = (Employee) auth.getPrincipal();
		
		employeePrincipal.setId(source.getId());
		
		return employeePrincipal;
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
	public Employee getCurrentEmployee() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.getPrincipal() instanceof Employee) {

			Employee employee = (Employee) auth.getPrincipal();
			return employee;
		} else {
			return null;
		}
	}

	@Override
	public Employee authenticate(String id, String password) throws Exception {
		Employee employee = employeeService.authenticate(id, password);

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
		return modelService.getDto(employee);
	}

	@Override
	public void deleteEmployeeProfilePictureByUuid(UUID uuid) {
		employeeService.deleteEmployeeProfilePictureByUuid(uuid);
	}

	@Override
	public void deleteAllEmployeeProfilePicture() {
		employeeService.deleteAllEmployeeProfilePicture();
	}
}
