package com.beanframework.console.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.console.ApplicationWebConstants;
import com.beanframework.console.ConsoleWebConstants;

@Controller
public class ConsoleController {

	@Value(ApplicationWebConstants.Path.APPLICATION_OVERVIEW)
	private String PATH_CONSOLE_APPLICATION_OVERVIEW;

	@Value(ConsoleWebConstants.View.LOGIN)
	private String VIEW_CONSOLE_LOGIN;

	@RequestMapping(ConsoleWebConstants.Path.CONSOLE)
	public String console(Model model, RedirectAttributes redirectAttributes) {
		return "redirect:" + PATH_CONSOLE_APPLICATION_OVERVIEW;
	}

	@RequestMapping(ConsoleWebConstants.Path.LOGIN)
	public String login() {
		return VIEW_CONSOLE_LOGIN;
	}
}
