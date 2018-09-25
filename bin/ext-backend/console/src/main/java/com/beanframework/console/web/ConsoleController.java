package com.beanframework.console.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.console.WebConsoleConstants;

@Controller
public class ConsoleController {

	@Value(WebConsoleConstants.Path.DASHBOARD)
	private String PATH_CONSOLE_DASHBOARD;

	@Value(WebConsoleConstants.View.LOGIN)
	private String VIEW_CONSOLE_LOGIN;

	@RequestMapping(WebConsoleConstants.Path.CONSOLE)
	public String console(Model model, RedirectAttributes redirectAttributes) {
		return "redirect:" + PATH_CONSOLE_DASHBOARD;
	}

	@RequestMapping(WebConsoleConstants.Path.LOGIN)
	public String login() {
		return VIEW_CONSOLE_LOGIN;
	}
}
