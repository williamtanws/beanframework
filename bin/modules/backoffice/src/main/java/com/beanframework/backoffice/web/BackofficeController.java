package com.beanframework.backoffice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.beanframework.backoffice.BackofficeWebConstants;

@Controller
public class BackofficeController {

	@Value(BackofficeWebConstants.View.LOGIN)
	private String VIEW_BACKOFFICE_LOGIN;

	@Value(BackofficeWebConstants.View.BACKOFFICE)
	private String VIEW_BACKOFFICE;

	@Value(BackofficeWebConstants.View.DASHBOARD)
	private String VIEW_BACKOFFICE_DASHBOARD;

	@GetMapping(BackofficeWebConstants.Path.LOGIN)
	public String login() {
		return VIEW_BACKOFFICE_LOGIN;
	}

	
	@GetMapping(BackofficeWebConstants.Path.BACKOFFICE)
	public String backoffice() {
		return VIEW_BACKOFFICE;
	}

	
	@GetMapping(BackofficeWebConstants.Path.DASHBOARD)
	public String dashboard() {
		return VIEW_BACKOFFICE_DASHBOARD;
	}
}
