package com.beanframework.user.domain;

import java.util.List;

import org.springframework.security.core.session.SessionInformation;

public class UserSession {

	private User user;
	private List<SessionInformation> sessionInformations;

	public UserSession(User user, List<SessionInformation> sessionInformations) {
		super();
		this.user = user;
		this.sessionInformations = sessionInformations;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<SessionInformation> getSessionInformations() {
		return sessionInformations;
	}

	public void setSessionInformations(List<SessionInformation> sessionInformations) {
		this.sessionInformations = sessionInformations;
	}
}