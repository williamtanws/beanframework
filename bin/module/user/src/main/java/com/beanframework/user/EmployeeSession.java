package com.beanframework.user;

import java.util.List;

import org.springframework.security.core.session.SessionInformation;

import com.beanframework.user.domain.Employee;

public class EmployeeSession {

	private Employee principalEmployee;
	private List<SessionInformation> sessionInformations;

	public EmployeeSession(Employee principalEmployee, List<SessionInformation> sessionInformations) {
		super();
		this.principalEmployee = principalEmployee;
		this.sessionInformations = sessionInformations;
	}

	public Employee getPrincipalEmployee() {
		return principalEmployee;
	}

	public void setPrincipalEmployee(Employee principalEmployee) {
		this.principalEmployee = principalEmployee;
	}

	public List<SessionInformation> getSessionInformations() {
		return sessionInformations;
	}

	public void setSessionInformations(List<SessionInformation> sessionInformations) {
		this.sessionInformations = sessionInformations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((principalEmployee.getId() == null) ? 0 : principalEmployee.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeSession other = (EmployeeSession) obj;
		if (principalEmployee.getId() == null) {
			if (other.principalEmployee.getId() != null)
				return false;
		} else if (!principalEmployee.getId().equals(other.principalEmployee.getId()))
			return false;
		return true;
	}
}