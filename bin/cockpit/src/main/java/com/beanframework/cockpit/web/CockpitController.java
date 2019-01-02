package com.beanframework.cockpit.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.cockpit.WebCockpitConstants;

@Controller
public class CockpitController {

	@Value(WebCockpitConstants.Path.COCKPIT)
	private String PATH_COCKPIT;

	@Value(WebCockpitConstants.Path.DASHBOARD)
	private String PATH_COCKPIT_DASHBOARD;

	@Value(WebCockpitConstants.View.LOGIN)
	private String VIEW_COCKPIT_LOGIN;

	@Value(WebCockpitConstants.View.DASHBOARD)
	private String VIEW_COCKPIT_DASHBOARD;

	@GetMapping(WebCockpitConstants.Path.COCKPIT)
	public String cockpit(Model model, RedirectAttributes redirectAttributes) {
		return "redirect:" + PATH_COCKPIT_DASHBOARD;
	}

	@GetMapping(WebCockpitConstants.Path.LOGIN)
	public String login() {
		return VIEW_COCKPIT_LOGIN;
	}

	@GetMapping(WebCockpitConstants.Path.DASHBOARD)
	public String dashboard() {
		return VIEW_COCKPIT_DASHBOARD;
	}
}
