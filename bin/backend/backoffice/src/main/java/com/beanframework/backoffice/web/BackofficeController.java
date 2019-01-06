package com.beanframework.backoffice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.backoffice.BackofficeWebConstants;

@Controller
public class BackofficeController {

	@Value(BackofficeWebConstants.Path.BACKOFFICE)
	private String PATH_BACKOFFICE;

	@Value(BackofficeWebConstants.Path.DASHBOARD)
	private String PATH_BACKOFFICE_DASHBOARD;

	@Value(BackofficeWebConstants.View.LOGIN)
	private String VIEW_BACKOFFICE_LOGIN;

	@Value(BackofficeWebConstants.View.DASHBOARD)
	private String VIEW_BACKOFFICE_DASHBOARD;

	@GetMapping(BackofficeWebConstants.Path.BACKOFFICE)
	public String backoffice(Model model, RedirectAttributes redirectAttributes) {
		return "redirect:" + PATH_BACKOFFICE_DASHBOARD;
	}

	@GetMapping(BackofficeWebConstants.Path.LOGIN)
	public String login() {
		return VIEW_BACKOFFICE_LOGIN;
	}

	@GetMapping(BackofficeWebConstants.Path.DASHBOARD)
	public String dashboard() {
		return VIEW_BACKOFFICE_DASHBOARD;
	}
}
