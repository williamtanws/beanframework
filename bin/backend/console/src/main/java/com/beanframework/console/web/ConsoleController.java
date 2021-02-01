package com.beanframework.console.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.console.ConsoleWebConstants;

@Controller
public class ConsoleController {

	@Value(ConsoleWebConstants.View.LOGIN)
	private String VIEW_CONSOLE_LOGIN;

	@Value(ConsoleWebConstants.View.CONSOLE)
	private String VIEW_CONSOLE;

	@RequestMapping(ConsoleWebConstants.Path.LOGIN)
	public String login() {
		return VIEW_CONSOLE_LOGIN;
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(ConsoleWebConstants.Path.CONSOLE)
	public String console(Model model, RedirectAttributes redirectAttributes) throws Exception {
		return VIEW_CONSOLE;
	}
}
